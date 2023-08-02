package com.bootcamp.springcamp.dtos.bootcamp;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotNull
    private String primaryPhone;

    @NotNull
    private String primaryEmail;
}
