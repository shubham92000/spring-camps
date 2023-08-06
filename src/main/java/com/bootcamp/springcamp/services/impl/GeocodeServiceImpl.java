package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.geocode.GeocodeResDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.services.GeocodeService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodeServiceImpl implements GeocodeService {
    private final Logger log = LoggerFactory.getLogger(GeocodeServiceImpl.class);
    private RestTemplate restTemplate;

    public GeocodeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GeocodeResDto getCoordinates(@NotNull String address) {
        if(address.isEmpty()){
            throw new CampApiException(HttpStatus.BAD_REQUEST, "address must not be empty");
        }
        GeocodeResDto resDto = restTemplate.getForObject("/"+address, GeocodeResDto.class);
        if(!(resDto == null || resDto.error == null || resDto.error.isEmpty())){
            throw new CampApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error in fetching coordinates for address");
        }
        log.info("address: "+address);
        log.info("location: "+resDto);
        return resDto;
    }
}
