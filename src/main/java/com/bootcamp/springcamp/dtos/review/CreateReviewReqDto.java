package com.bootcamp.springcamp.dtos.review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateReviewReqDto {
    @NotEmpty(message = "Please add a title for the review")
    @Size(max = 100)
    public String title;

    @NotEmpty(message = "Please add some text")
    public String text;

    @NotNull(message = "Please add a rating between 1 and 10")
    @Range(min = 1, max = 10)
    public Double rating;

    @NotNull
    public String courseId;
}
