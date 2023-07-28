package com.bootcamp.springcamp.dtos.bootcamp;

import com.bootcamp.springcamp.models.Address;
import com.bootcamp.springcamp.models.Location;
import com.bootcamp.springcamp.utils.Career;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateBootcampReqDto {
    @NotEmpty(message = "Please add a name")
    @Size(max = 50, message = "Name cannot be more than 50 characters")
    private String name;

    @NotEmpty(message = "Please add a description")
    @Size(max = 500, message = "Description cannot be more than 500 characters")
    private String description;

    private String website;
    private String phone;
    private String email;
}
