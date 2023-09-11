package com.bootcamp.springcamp.configurations;

import com.github.slugify.Slugify;
import io.minio.MinioClient;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    private EnvConfig envConfig;

    public AppConfig(EnvConfig envConfig) {
        this.envConfig = envConfig;
    }


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
				.rootUri(envConfig.geocodeUrl)
				.defaultHeader("key", envConfig.geocodeApiKey)
				.build();
	}

	@Bean
	public MinioClient minioClient(){
		return MinioClient.builder()
				.endpoint(envConfig.minioEndpoint, Integer.parseInt(envConfig.minioPort), Boolean.parseBoolean(envConfig.minioSecure))
				.credentials(envConfig.minioAccessKey, envConfig.minioSecretKey)
				.build();
	}
}
