package com.bootcamp.springcamp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvConfig {
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
}
