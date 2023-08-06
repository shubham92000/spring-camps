package com.bootcamp.springcamp.listeners;

import com.bootcamp.springcamp.models.Address;
import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.models.Course;
import com.bootcamp.springcamp.services.GeocodeService;
import com.bootcamp.springcamp.utils.LocationType;
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

import java.util.List;

@Component
public class BootcampListener extends AbstractMongoEventListener<Bootcamp> {
    private final Logger log = LoggerFactory.getLogger(BootcampListener.class);

    private MongoTemplate mongoTemplate;
    private GeocodeService geocodeService;
    private Slugify slg;

    private Boolean FETCH_COORDINATES = false;

    public BootcampListener(MongoTemplate mongoTemplate, GeocodeService geocodeService, Slugify slg) {
        super();
        this.mongoTemplate = mongoTemplate;
        this.geocodeService = geocodeService;
        this.slg = slg;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Bootcamp> event) {
        super.onBeforeConvert(event);

        String result = slg.slugify(event.getSource().getName());
        event.getSource().setSlug(result);

        if(event.getSource().getAddress() != null && FETCH_COORDINATES){
            Address addressModel = event.getSource().getAddress();
            String address = addressModel.getBuildingInfo() + addressModel.getStreet() + addressModel.getCity()
                    + addressModel.getState() + addressModel.getCountry() + addressModel.getZipcode();
            event.getSource().getLocation().setFormattedAddress(address);
            var coordinates = geocodeService.getCoordinates(address);
            event.getSource().getLocation().setType(LocationType.Point);
            event.getSource().getLocation().setCoordinates(List.of(
                    Double.parseDouble(coordinates.getLoc().getLon()),
                    Double.parseDouble(coordinates.getLoc().getLat())
            ));
        }
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
