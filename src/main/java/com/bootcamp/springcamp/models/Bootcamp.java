package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Career;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "bootcamps")
public class Bootcamp {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private String slug;
    private String description;
    private String website;
    private String phone;
    private String email;
    private String address;
    private Location location;
    private List<Career> careers;
    private Double averageRating;
    private Double averageCost;
    private String photo;
    private Boolean housing;
    private Boolean jobAssistance;
    private Boolean jobGuarantee;
    private Boolean acceptGI;
    private LocalDateTime createdOn;

    @DocumentReference
    private User user;

    @ReadOnlyProperty
    @DocumentReference(lookup="{'bootcamp':?#{#self._id} }", lazy = true)
    public List<Course> courses;


}
