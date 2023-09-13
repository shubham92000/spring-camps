package com.bootcamp.springcamp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import org.slf4j.Logger;

@DataMongoTest(
        properties = {
//                "db_uri=mongodb+srv://shubham:shubham@cluster0.xzpcx63.mongodb.net/spring-camp2?retryWrites=true&w=majority",
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
public class BootcampRepoTest {

    private Logger logger = LoggerFactory.getLogger(BootcampRepoTest.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ApplicationContext context;

    @Test
    public void test(){
        assert mongoTemplate!=null;
        assert context!=null;
        logger.info("count: -> "+context.getBeanDefinitionCount());
        for(var c: context.getBeanDefinitionNames()){
            System.out.println("--> "+c);
        }
    }
}
