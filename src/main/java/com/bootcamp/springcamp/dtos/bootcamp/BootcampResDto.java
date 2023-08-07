package com.bootcamp.springcamp.dtos.bootcamp;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BootcampResDto {
    private String id;
    private String name;
    private String slug;
    private String description;
    private String website;
    private String primaryPhone;
    private String primaryEmail;
    private List<String> emails;
    private List<String> phones;
    private String formattedAddress;
    private List<String> careers;
    private Map<String, Double> ratings;
    private Map<String, Double> costs;
    private List<Photo> photos;
    private Boolean housing;
    private Boolean jobAssistance;
    private Boolean jobGuarantee;
    private Boolean acceptGI;
    private String mode;
}
