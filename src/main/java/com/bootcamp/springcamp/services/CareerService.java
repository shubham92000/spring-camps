package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.career.CareerResDto;
import com.bootcamp.springcamp.dtos.career.CreateCareerReqDto;
import com.bootcamp.springcamp.dtos.career.UpdateCareerReqDto;

import java.util.List;

public interface CareerService {
       List<CareerResDto> getAllCareers();
       List<CareerResDto> addCareer(CreateCareerReqDto careersInfo);
       CareerResDto updateCareer(String id, UpdateCareerReqDto careerInfo);
       String deleteCareer(String id);
}
