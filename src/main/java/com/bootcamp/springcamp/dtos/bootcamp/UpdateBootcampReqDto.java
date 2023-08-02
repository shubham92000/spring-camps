package com.bootcamp.springcamp.dtos.bootcamp;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateBootcampReqDto {
    private String name;
    private String description;
    private String website;
    private AddressReqDto address;
    private List<String> careers;
    private Boolean housing;
    private Boolean jobAssistance;
    private Boolean jobGuarantee;
    private Boolean acceptGI;
}
