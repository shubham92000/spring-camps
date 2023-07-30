package com.bootcamp.springcamp.dtos.course;

import com.bootcamp.springcamp.utils.Skill;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCourseReqDto {
    private String title;
    private String description;
    private String weeks;
    private Double tuition;
    private Skill minimumSkill;
    private Boolean scholarshipAvailable;
}
