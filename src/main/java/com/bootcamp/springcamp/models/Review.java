package com.bootcamp.springcamp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "reviews")
@CompoundIndexes({
        @CompoundIndex(
                name = "bootcamp_user",
                def = "{'bootcamp' : 1, 'user': 1}"
        )
})
public class Review {
    @Id
    private String id;
    public String title;
    public String text;
    public Integer rating;
    public LocalDateTime createdAt;

    @DocumentReference
    public Bootcamp bootcamp;

    @DocumentReference
    public User user;

    public Review(String title, String text, Integer rating, LocalDateTime createdAt, Bootcamp bootcamp, User user) {
        this.title = title;
        this.text = text;
        this.rating = rating;
        this.createdAt = createdAt;
        this.bootcamp = bootcamp;
        this.user = user;
    }
}
