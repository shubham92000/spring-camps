package com.bootcamp.springcamp.services.mock;

import com.bootcamp.springcamp.dtos.geocode.GeocodeResDto;
import com.bootcamp.springcamp.dtos.geocode.Location;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.services.GeocodeService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service("geocode_service_mock")
public class GeocodeServiceMock implements GeocodeService {
    private final Logger log = LoggerFactory.getLogger(GeocodeServiceMock.class);

    @Override
    public String message() {
        return "using geocode_service_mock";
    }

    @Override
    public GeocodeResDto getCoordinates(@NotNull String address) {
        if(address.isEmpty()){
            throw new CampApiException(HttpStatus.BAD_REQUEST, "address must not be empty");
        }
        GeocodeResDto resDto = new GeocodeResDto(new Location("19.25", "73.13"), null);
        if(!(resDto == null || resDto.error == null || resDto.error.isEmpty())){
            throw new CampApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error in fetching coordinates for address");
        }
        log.info("address: "+address);
        log.info("location: "+resDto);
        return resDto;
    }
}
