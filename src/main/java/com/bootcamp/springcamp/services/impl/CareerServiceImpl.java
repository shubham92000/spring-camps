package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.career.CareerResDto;
import com.bootcamp.springcamp.dtos.career.CreateCareerReqDto;
import com.bootcamp.springcamp.dtos.career.UpdateCareerReqDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Career;
import com.bootcamp.springcamp.repos.CareerRepo;
import com.bootcamp.springcamp.services.CareerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerServiceImpl implements CareerService {
    private ModelMapper mapper;
    private CareerRepo careerRepo;

    public CareerServiceImpl(ModelMapper mapper, CareerRepo careerRepo) {
        this.mapper = mapper;
        this.careerRepo = careerRepo;
    }

    @Override
    public List<CareerResDto> getAllCareers() {
        return careerRepo.findAll()
                .stream().map(career -> mapper.map(career, CareerResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CareerResDto> addCareer(CreateCareerReqDto careersInfo) {
        var careers = careersInfo.names
                .stream().map(Career::new)
                .collect(Collectors.toList());
        careers = careerRepo.saveAll(careers);
        return careers.stream().map(career -> mapper.map(career, CareerResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CareerResDto updateCareer(String id, UpdateCareerReqDto careerInfo) {
        var career = careerRepo.findById(id).orElseThrow(() ->
                new CampApiException(HttpStatus.NOT_FOUND, String.format("career with id %s not found", id)));
        career.name = careerInfo.name;
        careerRepo.save(career);
        return mapper.map(career, CareerResDto.class);
    }

    @Override
    public String deleteCareer(String id) {
        careerRepo.deleteById(id);
        return id;
    }
}
