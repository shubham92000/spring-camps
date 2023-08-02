package com.bootcamp.springcamp;

import io.minio.MinioClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableMongoAuditing(modifyOnCreate = false)
public class SpringCampApplication {
	@Value("${minio.endpoint}")
	public String minioEndpoint;
	@Value("${minio.port}")
	public String minioPort;
	@Value("${minio.secure}")
	public String minioSecure;
	@Value("${minio.accessKey}")
	public String minioAccessKey;
	@Value("${minio.secretKey}")
	public String minioSecretKey;

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public MinioClient minioClient(){
		return MinioClient.builder()
				.endpoint(minioEndpoint, Integer.parseInt(minioPort), Boolean.parseBoolean(minioSecure))
				.credentials(minioAccessKey, minioSecretKey)
				.build();
	}

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(SpringCampApplication.class, args);
	}

}
