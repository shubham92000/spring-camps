package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.bootcamp.BootcampResDto;
import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.services.impl.BootcampServiceImpl;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BootcampServiceTest {
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

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BootcampRepo bootcampRepo;

    @Mock
    private CareerService careerService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private MinioClient minioClient;

    @Mock
    private GeocodeService geocodeService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BootcampServiceImpl bootcampService;

    @Test
    public void test(){
        List<BootcampResDto> bootcamps = bootcampService.getAllBootcamps();
        assert bootcamps.size()==0;
    }
}
