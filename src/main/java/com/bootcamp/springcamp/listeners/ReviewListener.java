package com.bootcamp.springcamp.listeners;

import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Review;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.ReviewRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ReviewListener extends AbstractMongoEventListener<Review> {
    private final Logger log = LoggerFactory.getLogger(CourseListener.class);
    private BootcampRepo bootcampRepo;
    private ReviewRepo reviewRepo;

    public ReviewListener(BootcampRepo bootcampRepo, ReviewRepo reviewRepo) {
        this.bootcampRepo = bootcampRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Review> event) {
        super.onAfterSave(event);
        var review = event.getSource();
        var bootcamp = event.getSource().getBootcamp();
        bootcamp.getRatings().putIfAbsent(review.getId(), review.rating);
        bootcampRepo.save(bootcamp);
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Review> event) {
        super.onBeforeDelete(event);

        String reviewId = event.getSource().get("_id").toString();
        log.info("reviewId: "+reviewId);

        var review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("review with id %s not found", reviewId)));
        var bootcamp = review.bootcamp;
        bootcamp.getRatings().remove(reviewId);

        bootcampRepo.save(bootcamp);
    }
}
