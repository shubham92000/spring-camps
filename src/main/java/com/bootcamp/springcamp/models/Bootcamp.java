package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Mode;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Document(collection = "bootcamps")
public class Bootcamp {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotEmpty(message = "Please add a name")
    @Size(max = 50, message = "Name cannot be more than 50 characters")
    private String name;

    private String slug;

    @NotEmpty(message = "Please add a description")
    @Size(max = 500, message = "Description cannot be more than 500 characters")
    private String description;

//    @Pattern()
    private String website;

    @Size(max = 20, message = "Phone no cannot be longer than 20 characters")
    private String primaryPhone;

    private String primaryEmail;

    private List<String> emails;

    private List<String> phones;

    @NotNull
    private Address address;

    @NotNull
    @GeoSpatialIndexed(name = "location_index", type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Location location;

    @NotNull
    @Size(min = 1, message = "add atleast one career option")
    private List<String> careers;

    private Map<String, Double> ratings;

    private Map<String, Double> costs;

    private List<Photo> photos;

    private Boolean housing;
    private Boolean jobAssistance;
    private Boolean jobGuarantee;
    private Boolean acceptGI;
    private String mode;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime lastModifiedOn;

    @DocumentReference
    private User user;

    @ReadOnlyProperty
    @DocumentReference(lookup="{'bootcamp':?#{#self._id} }", lazy = true)
    public List<Course> courses;

    public Bootcamp() {
        this.emails = new ArrayList<>();
        this.phones = new ArrayList<>();
        this.photos = new ArrayList<>();
        this.ratings = new HashMap<>();
        this.costs = new HashMap<>();
        this.housing = false;
        this.jobAssistance = false;
        this.jobGuarantee = false;
        this.acceptGI = false;
        this.mode = Mode.OFFLINE.name();
    }
}
