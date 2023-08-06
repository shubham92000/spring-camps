package com.bootcamp.springcamp.dtos.geocode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeocodeResDto {
    public Location loc;
    public String error;
}
