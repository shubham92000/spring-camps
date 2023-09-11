package com.bootcamp.springcamp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Component;

@Configuration
@EnableMongoAuditing(modifyOnCreate = false)
public class MongoConfig {

}
