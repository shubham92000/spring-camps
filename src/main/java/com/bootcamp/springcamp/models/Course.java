package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Skill;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "courses")
public class Course {
    @Id
    private String id;

    @NotEmpty(message = "Please add a course title")
    private String title;

    @NotEmpty(message = "Please add a description")
    private String description;

    @NotEmpty(message = "Please add number of weeks")
    private String weeks;

    @NotNull(message = "Please add a tuition cost")
    private Double tuition;

    @NotNull(message = "Please add a minimum skill")
    private Skill minimumSkill;

    private Boolean scholarshipAvailable;
    private LocalDateTime createdAt;

    @DocumentReference
    private Bootcamp bootcamp;

    @DocumentReference
    private User user;

    public Course(String title, String description, String weeks, Double tuition, Skill minimumSkill, Bootcamp bootcamp, User user) {
        this.title = title;
        this.description = description;
        this.weeks = weeks;
        this.tuition = tuition;
        this.minimumSkill = minimumSkill;
        this.scholarshipAvailable = false;
        this.createdAt = LocalDateTime.now();
        this.bootcamp = bootcamp;
        this.user = user;
    }
}
