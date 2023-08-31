package com.bootcamp.springcamp.dtos.bootcamp;

import com.bootcamp.springcamp.models.Bootcamp;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetBootcampInRadiusResDto {
    private Long count;
    private List<Bootcamp> bootcamps;
}
