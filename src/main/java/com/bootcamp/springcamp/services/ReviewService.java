package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.course.CourseResDto;
import com.bootcamp.springcamp.dtos.review.CreateReviewReqDto;
import com.bootcamp.springcamp.dtos.review.ReviewResDto;
import com.bootcamp.springcamp.dtos.review.UpdateReviewReqDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReviewService {
    List<ReviewResDto> getAllReviewsOfCourse(String courseId);

    List<ReviewResDto> getAllReviewsOfBootcamp(String bootcampId);
    ReviewResDto getReview(String reviewId);
    ReviewResDto createReview(CreateReviewReqDto createReviewReqDto, Authentication authentication);
    ReviewResDto updateReview(String reviewId, UpdateReviewReqDto updateReviewReqDto, Authentication authentication);
    String deleteReview(String reviewId);
}
