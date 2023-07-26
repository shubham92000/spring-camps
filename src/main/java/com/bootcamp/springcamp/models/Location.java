package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.LocationType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {
    private LocationType type;
    private List<Double> coordinates;
    private String formattedAddress;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
