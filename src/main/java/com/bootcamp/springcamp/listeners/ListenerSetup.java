package com.bootcamp.springcamp.listeners;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ListenerSetup {
    private MongoTemplate mongoTemplate;

    public ListenerSetup(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public BootcampListener bootcampListener(){
        return new BootcampListener(mongoTemplate);
    }
}
