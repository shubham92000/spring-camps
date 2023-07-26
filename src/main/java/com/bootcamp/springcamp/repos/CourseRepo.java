package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepo extends MongoRepository<Course, String> {

}
