package com.bootcamp.springcamp.dtos.career;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCareerReqDto {
    @NotEmpty
    public String name;
}
