package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.models.Course;
import com.bootcamp.springcamp.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepo extends MongoRepository<Review, String> {
    List<Review> findByCourse(Course course);
    List<Review> findByBootcamp(Bootcamp bootcamp);
}
