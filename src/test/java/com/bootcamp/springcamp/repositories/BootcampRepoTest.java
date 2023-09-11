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

@DataMongoTest
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
//        assert context.getBeanDefinitionCount()==0;
        logger.info("count: -> "+context.getBeanDefinitionCount());
        for(var c: context.getBeanDefinitionNames()){
            System.out.println("--> "+c);
        }
    }
}
