package com.bootcamp.springcamp.listeners;

import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.models.Course;
import com.github.slugify.Slugify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class BootcampListener extends AbstractMongoEventListener<Bootcamp> {
    private final Logger log = LoggerFactory.getLogger(BootcampListener.class);

    private MongoTemplate mongoTemplate;
    private Slugify slg;

    public BootcampListener(MongoTemplate mongoTemplate, Slugify slg) {
        super();
        this.mongoTemplate = mongoTemplate;
        this.slg = slg;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Bootcamp> event) {
        super.onBeforeConvert(event);
        String result = slg.slugify(event.getSource().getName());
        event.getSource().setSlug(result);
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Bootcamp> event) {
        super.onAfterSave(event);
//        log.info("id: "+event.getSource().getId());

    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Bootcamp> event) {
        super.onBeforeDelete(event);
//        log.info("id present: "+event.getSource().containsKey("id"));
//        log.info("_id present: "+event.getSource().containsKey("_id"));
//        log.info("_id : "+event.getSource().get("_id"));

        Object bootcampId = event.getSource().get("_id");
        if(bootcampId != null){
            var courses = mongoTemplate.findAllAndRemove(Query.query(Criteria.where("bootcamp").is(bootcampId)), Course.class, "courses");
            log.info("size: "+courses.size());
            courses.forEach(course -> log.info("course_id :"+course.getId()));
        }
    }
}
