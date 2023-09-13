package com.bootcamp.springcamp.integration;

import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.services.impl.GeocodeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "jwt-secret=10f6d3ce9d854d1ebfc1ca7d1981fafc122a9970093382f2c5c72cfa6ab47572",
                "jwt-expiration=606800000",
                "log_level=DEBUG",
                "mongo_log_level=DEBUG",
                "minio_endpoint= ",
                "minio_port= ",
                "minio_accessKey= ",
                "minio_secretKey= ",
                "minio_image_upload_bucket= ",
                "geocode_url= ",
                "geocode_apikey= ",
                "fetch_coordinates= ",
        }
)
@AutoConfigureMockMvc
public class BootcampControllerITTest {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MinioClient minioClient;

    @MockBean
    private GeocodeServiceImpl geocodeServiceImpl;

    @Autowired
    private BootcampRepo bootcampRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setup(){
//        mongoTemplate.createCollection(Bootcamp.class);
    }

    @Test
    public void notNull(){
        assert context!=null;
        System.out.println("count: -> "+context.getBeanDefinitionCount());
        for(var c: context.getBeanDefinitionNames()){
            System.out.println("--> "+c);
        }
    }
}
