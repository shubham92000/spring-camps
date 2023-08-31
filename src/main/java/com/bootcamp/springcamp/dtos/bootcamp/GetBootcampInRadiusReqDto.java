package com.bootcamp.springcamp.dtos.bootcamp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetBootcampInRadiusReqDto {
    private String zipcode;

    // distance in payload is in kilometers
    private String distance;
}
