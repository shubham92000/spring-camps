package com.bootcamp.springcamp.models;

import com.bootcamp.springcamp.utils.LocationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {
    @NotNull
    private LocationType type;
    @NotNull
    private List<Double> coordinates;
    private String formattedAddress;
}
