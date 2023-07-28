package com.bootcamp.springcamp.dtos.bootcamp;

import com.bootcamp.springcamp.utils.Career;
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
    private String phone;
    private String email;
    private String formattedAddress;
    private List<Career> careers;
    private Map<String, Double> ratings;
    private Map<String, Double> costs;
    private String photo;
    private Boolean housing;
    private Boolean jobAssistance;
    private Boolean jobGuarantee;
    private Boolean acceptGI;
}
