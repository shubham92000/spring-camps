package com.bootcamp.springcamp.listeners;

import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Course;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.CourseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CourseListener extends AbstractMongoEventListener<Course> {
    private final Logger log = LoggerFactory.getLogger(CourseListener.class);
    private BootcampRepo bootcampRepo;
    private CourseRepo courseRepo;

    public CourseListener(BootcampRepo bootcampRepo, CourseRepo courseRepo) {
        this.bootcampRepo = bootcampRepo;
        this.courseRepo = courseRepo;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Course> event) {
        super.onAfterSave(event);
        var course = event.getSource();
        var bootcamp = event.getSource().getBootcamp();
        bootcamp.getCosts().putIfAbsent(course.getId(), course.getTuition());
        bootcampRepo.save(bootcamp);
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Course> event) {
        super.onBeforeDelete(event);

        String courseId = event.getSource().get("_id").toString();
        log.info("courseId: "+courseId);

        var course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("course with id %s not found", courseId)));
        var bootcamp = course.getBootcamp();
        bootcamp.getCosts().remove(courseId);

        bootcampRepo.save(bootcamp);
    }
}
