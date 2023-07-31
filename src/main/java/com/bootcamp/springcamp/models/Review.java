package com.bootcamp.springcamp.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Document(collection = "reviews")
@CompoundIndexes({
        @CompoundIndex(
                name = "bootcamp_course_user",
                def = "{'bootcamp' : 1, 'course': 1,  'user': 1}"
        )
})
public class Review {
    @Id
    private String id;

    @NotEmpty(message = "Please add a title for the review")
    @Size(max = 100)
    public String title;

    @NotEmpty(message = "Please add some text")
    public String text;

    @NotNull(message = "Please add a rating between 1 and 10")
    @Size(min = 1, max = 10)
    public Double rating;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime lastModifiedOn;

    @DocumentReference
    public Bootcamp bootcamp;

    @DocumentReference
    public Course course;

    @DocumentReference
    public User user;

    public Review() {

    }
}
