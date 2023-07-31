package com.bootcamp.springcamp.dtos.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateReviewReqDto {
    public String title;
    public String text;
    public Double rating;
}
