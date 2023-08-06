package com.bootcamp.springcamp;

import com.github.slugify.Slugify;
import io.minio.MinioClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.client.RestTemplate;

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

	@Value("${geocode.apikey}")
	public String geocodeApiKey;

	@Value("${geocode.url}")
	public String geocodeUrl;

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public Slugify slugify(){
		return Slugify.builder().underscoreSeparator(true).build();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.rootUri(geocodeUrl)
				.defaultHeader("key", geocodeApiKey)
				.build();
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
