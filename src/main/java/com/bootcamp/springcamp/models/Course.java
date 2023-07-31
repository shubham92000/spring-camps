package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.Skill;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Document(collection = "courses")
@CompoundIndexes({
        @CompoundIndex(
                name = "title_bootcamp",
                def = "{'bootcamp' : 1, 'title': 1}"
        )
})
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
    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime lastModifiedOn;

    @DocumentReference
    private Bootcamp bootcamp;

    @DocumentReference
    private User user;

    public Course() {
        this.scholarshipAvailable = false;
    }
}
