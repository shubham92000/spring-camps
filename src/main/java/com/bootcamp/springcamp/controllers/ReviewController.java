package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.review.CreateReviewReqDto;
import com.bootcamp.springcamp.dtos.review.ReviewResDto;
import com.bootcamp.springcamp.dtos.review.UpdateReviewReqDto;
import com.bootcamp.springcamp.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/course/{id}")
    ResponseEntity<List<ReviewResDto>> getAllReviewsOfCourse(@PathVariable("id") String courseId){
        var response = reviewService.getAllReviewsOfCourse(courseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bootcamp/{id}")
    ResponseEntity<List<ReviewResDto>> getAllReviewsOfBootcamp(@PathVariable("id") String bootcampId){
        var response = reviewService.getAllReviewsOfCourse(bootcampId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ReviewResDto> getReview(@PathVariable("id") String reviewId){
        var response = reviewService.getReview(reviewId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<ReviewResDto> createReview(@Valid @RequestBody CreateReviewReqDto createReviewReqDto, Authentication authentication){
        var response = reviewService.createReview(createReviewReqDto, authentication);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<ReviewResDto> updateReview(@PathVariable("id") String reviewId,@Valid @RequestBody UpdateReviewReqDto updateReviewReqDto, Authentication authentication){
        var response = reviewService.updateReview(reviewId, updateReviewReqDto, authentication);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteReview(@PathVariable("id") String reviewId){
        var response = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(response);
    }
}
