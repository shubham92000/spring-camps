package com.bootcamp.springcamp.models;

import jakarta.validation.constraints.NotEmpty;

public class Address {
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
