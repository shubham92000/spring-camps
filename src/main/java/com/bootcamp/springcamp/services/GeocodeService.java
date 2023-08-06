package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.geocode.GeocodeResDto;

public interface GeocodeService {
    GeocodeResDto getCoordinates(String address);
}
