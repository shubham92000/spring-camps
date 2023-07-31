package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.review.CreateReviewReqDto;
import com.bootcamp.springcamp.dtos.review.ReviewResDto;
import com.bootcamp.springcamp.dtos.review.UpdateReviewReqDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Review;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.CourseRepo;
import com.bootcamp.springcamp.repos.ReviewRepo;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ModelMapper mapper;
    private UserRepo userRepo;
    private ReviewRepo reviewRepo;
    private BootcampRepo bootcampRepo;
    private CourseRepo courseRepo;

    public ReviewServiceImpl(ModelMapper mapper, UserRepo userRepo, ReviewRepo reviewRepo, BootcampRepo bootcampRepo, CourseRepo courseRepo) {
        this.mapper = mapper;
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
        this.bootcampRepo = bootcampRepo;
        this.courseRepo = courseRepo;
    }

    @Override
    public List<ReviewResDto> getAllReviewsOfCourse(String courseId) {
        var course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("course with id %s not found", courseId)));

        return reviewRepo.findByCourse(course)
                .stream().map(review -> mapper.map(review, ReviewResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResDto> getAllReviewsOfBootcamp(String bootcampId) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not found", bootcampId)));

        return reviewRepo.findByBootcamp(bootcamp)
                .stream().map(review -> mapper.map(review, ReviewResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResDto getReview(String reviewId) {
        var review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("review with id %s not found", reviewId)));

        return mapper.map(review, ReviewResDto.class);
    }

    @Override
    public ReviewResDto createReview(CreateReviewReqDto createReviewReqDto, Authentication authentication) {
        var user = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", authentication.getName())));

        var course = courseRepo.findById(createReviewReqDto.courseId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not found", createReviewReqDto.courseId)));

        var review = mapper.map(createReviewReqDto, Review.class);

        review.bootcamp = course.getBootcamp();
        review.course = course;
        review.user = user;

        review = reviewRepo.save(review);

        return mapper.map(review, ReviewResDto.class);
    }

    @Override
    public ReviewResDto updateReview(String reviewId, UpdateReviewReqDto updateReviewReqDto, Authentication authentication) {
        var review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("review with id %s not found", reviewId)));

        var reviewCreator = review.user;
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", authentication.getName())));

        if(!reviewCreator.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, "user not allowed to update review");
        }

        if(updateReviewReqDto.title != null){
            review.title = updateReviewReqDto.title;
        }

        if(updateReviewReqDto.text != null){
            review.text = updateReviewReqDto.text;
        }

        if(updateReviewReqDto.rating != null){
            review.rating = updateReviewReqDto.rating;
        }

        review = reviewRepo.save(review);
        return mapper.map(review, ReviewResDto.class);
    }

    @Override
    public String deleteReview(String reviewId) {
        reviewRepo.deleteById(reviewId);
        return reviewId;
    }
}
