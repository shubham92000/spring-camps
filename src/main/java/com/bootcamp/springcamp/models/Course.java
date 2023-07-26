package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Skill;
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
    public String title;
    public String description;
    public String weeks;
    public Double tuition;
    public Skill minimumSkill;
    public Boolean scholarshipAvailable;
    public LocalDateTime createdAt;

    @DocumentReference
    public Bootcamp bootcamp;

    @DocumentReference
    public User user;

    public Course(String title, String description, String weeks, Double tuition, Skill minimumSkill, Boolean scholarshipAvailable, LocalDateTime createdAt, Bootcamp bootcamp, User user) {
        this.title = title;
        this.description = description;
        this.weeks = weeks;
        this.tuition = tuition;
        this.minimumSkill = minimumSkill;
        this.scholarshipAvailable = scholarshipAvailable;
        this.createdAt = createdAt;
        this.bootcamp = bootcamp;
        this.user = user;
    }
}
