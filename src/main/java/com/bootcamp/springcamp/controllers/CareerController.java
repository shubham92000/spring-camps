package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.career.CareerResDto;
import com.bootcamp.springcamp.dtos.career.CreateCareerReqDto;
import com.bootcamp.springcamp.dtos.career.UpdateCareerReqDto;
import com.bootcamp.springcamp.services.CareerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/careers")
public class CareerController {
    private final Logger log = LoggerFactory.getLogger(CareerController.class);
    private CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public ResponseEntity<List<CareerResDto>> getAllCareers(){
        var res = careerService.getAllCareers();
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    public ResponseEntity<List<CareerResDto>> addCareer(@Valid @RequestBody CreateCareerReqDto careersInfo){
        log.info("careersInfo: "+careersInfo);
        var res = careerService.addCareer(careersInfo);
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<CareerResDto> updateCareer(@PathVariable String id,@Valid @RequestBody UpdateCareerReqDto careerInfo){
        var res = careerService.updateCareer(id, careerInfo);
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCareer(@PathVariable String id){
        var res = careerService.deleteCareer(id);
        return ResponseEntity.ok(res);
    }
}
