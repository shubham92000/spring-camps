package com.bootcamp.springcamp.dtos.course;

import com.bootcamp.springcamp.utils.Skill;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseResDto {
    private String id;
    private String title;
    private String description;
    private String weeks;
    private Double tuition;
    private Skill minimumSkill;
    private Boolean scholarshipAvailable;
    private String bootcampId;
}
