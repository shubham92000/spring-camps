package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Bootcamp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BootcampRepo extends MongoRepository<Bootcamp, String> {

}
