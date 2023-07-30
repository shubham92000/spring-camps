package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Bootcamp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BootcampRepo extends MongoRepository<Bootcamp, String> {
    Optional<Bootcamp> findByName(String name);
}
