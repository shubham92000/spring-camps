package com.bootcamp.springcamp.dtos.course;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCourseReqDto {
    @NotEmpty(message = "Please add a course title")
    private String title;

    @NotEmpty(message = "Please add a description")
    private String description;

    @NotEmpty(message = "Please add number of weeks")
    private String weeks;

    @NotNull(message = "Please add a tuition cost")
    private Double tuition;

    @NotNull(message = "Please add a minimum skill")
    private String skillType;

    private Boolean scholarshipAvailable;

    @NotNull(message = "Please provide the bootcampId")
    private String bootcampId;
}
