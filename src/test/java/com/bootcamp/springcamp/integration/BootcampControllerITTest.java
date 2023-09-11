package com.bootcamp.springcamp.integration;

import com.bootcamp.springcamp.repos.BootcampRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
public class BootcampControllerITTest {

    static {
        System.setProperty("jwt-secret", "10f6d3ce9d854d1ebfc1ca7d1981fafc122a9970093382f2c5c72cfa6ab47572");
        System.setProperty("jwt-expiration", "606800000");
        System.setProperty("log_level", "DEBUG");
        System.setProperty("mongo_log_level", "DEBUG");
        System.setProperty("minio_endpoint", "");
        System.setProperty("minio_port", "");
        System.setProperty("minio_accessKey", "");
        System.setProperty("minio_secretKey", "");
        System.setProperty("minio_image_upload_bucket", "");
        System.setProperty("geocode_url", "");
        System.setProperty("geocode_apikey", "");
        System.setProperty("fetch_coordinates", "");
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BootcampRepo bootcampRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        bootcampRepo.deleteAll();
    }

    @Test
    public void asd(){
        assert context!=null;
        System.out.println("count: -> "+context.getBeanDefinitionCount());
        for(var c: context.getBeanDefinitionNames()){
            System.out.println("--> "+c);
        }
    }
}
