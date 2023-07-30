package com.bootcamp.springcamp.dtos.bootcamp;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressReqDto {
    @NotEmpty
    private String buildingInfo;
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String zipcode;
    @NotEmpty
    private String country;
}
