package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepo extends MongoRepository<Review, String> {

}
