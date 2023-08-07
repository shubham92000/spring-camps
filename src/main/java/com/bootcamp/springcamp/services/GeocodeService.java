package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.geocode.GeocodeResDto;
import jakarta.validation.constraints.NotNull;

public interface GeocodeService {
    GeocodeResDto getCoordinates(@NotNull String address);
}
