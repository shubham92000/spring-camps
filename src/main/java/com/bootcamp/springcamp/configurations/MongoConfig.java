package com.bootcamp.springcamp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.stereotype.Component;

//@Component
//public class MongoConfig extends AbstractMongoClientConfiguration {
//    @Value("spring.data.mongodb.database")
//    private String database;
//
//    @Override
//    protected boolean autoIndexCreation() {
//        return true;
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        System.out.println(database);
//        return database;
//    }
//}
