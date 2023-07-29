package com.bootcamp.springcamp.models;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Document(collection = "careers")
public class Career {
    public String id;
    @Indexed(unique = true)
    public String name;
    public LocalDateTime createdAt;

    public Career() {
        this.createdAt = LocalDateTime.now();
    }

    public Career(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }
}
