package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepo extends MongoRepository<Course, String> {
    List<Course> findByBootcamp(Bootcamp bootcamp);
}
