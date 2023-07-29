package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Career;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CareerRepo extends MongoRepository<Career, String> {

}
