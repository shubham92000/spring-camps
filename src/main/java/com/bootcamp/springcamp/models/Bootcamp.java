package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Career;
import jakarta.validation.constraints.*;
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
    private String phone;

    private String email;

    @NotEmpty(message = "Please add an address")
    private String address;

    @NotNull
    private Location location;

    @NotNull
    @Size(min = 1, message = "add atleat one career option")
    private List<Career> careers;

    @Size(min = 1, message = "Rating must be atleast 1")
    @Size(min = 10, message = "Rating cannot be more than 10")
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

    public Bootcamp() {
        this.averageRating = 0.0;
        this.averageCost = 0.0;
        this.housing = false;
        this.jobAssistance = false;
        this.jobGuarantee = false;
        this.acceptGI = false;
        this.createdOn = LocalDateTime.now();
    }
}
