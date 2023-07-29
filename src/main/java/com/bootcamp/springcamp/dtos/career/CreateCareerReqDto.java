package com.bootcamp.springcamp.dtos.career;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCareerReqDto {
    @Size(min = 1)
    public List<@NotEmpty String> names;
}
