package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {

}
