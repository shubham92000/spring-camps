package com.bootcamp.springcamp.dtos.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewResDto {
    private String id;
    public String title;
    public String text;
    public Double rating;
    public String bootcampId;
    public String courseId;
    public String userId;
}
