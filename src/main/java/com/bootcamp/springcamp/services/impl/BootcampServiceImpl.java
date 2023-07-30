package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.controllers.BootcampController;
import com.bootcamp.springcamp.dtos.bootcamp.BootcampResDto;
import com.bootcamp.springcamp.dtos.bootcamp.CreateBootcampReqDto;
import com.bootcamp.springcamp.dtos.bootcamp.UpdateBootcampReqDto;
import com.bootcamp.springcamp.dtos.career.CareerResDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Address;
import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.services.BootcampService;
import com.bootcamp.springcamp.services.CareerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BootcampServiceImpl implements BootcampService {

    private final Logger log = LoggerFactory.getLogger(BootcampController.class);
    private ModelMapper mapper;
    private BootcampRepo bootcampRepo;
    private CareerService careerService;
    private UserRepo userRepo;

    public BootcampServiceImpl(ModelMapper mapper, BootcampRepo bootcampRepo, CareerService careerService, UserRepo userRepo) {
        this.mapper = mapper;
        this.bootcampRepo = bootcampRepo;
        this.careerService = careerService;
        this.userRepo = userRepo;
    }

    @Override
    public List<BootcampResDto> getAllBootcamps() {
        var bootcamps = bootcampRepo.findAll()
                .stream().map(bootcamp -> mapper.map(bootcamp, BootcampResDto.class))
                .collect(Collectors.toList());
        return bootcamps;
    }

    @Override
    public BootcampResDto getBootcamp(String id) {
        var bootcamp = bootcampRepo.findById(id)
                .orElseThrow(() -> new CampApiException(HttpStatus.OK, String.format("bootcamp with id %s not found", id)));

        var bootcampRes = mapper.map(bootcamp, BootcampResDto.class);
        return bootcampRes;
    }

    @Override
    public BootcampResDto createBootcamp(CreateBootcampReqDto createBootcampReqDto, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new CampApiException(HttpStatus.UNAUTHORIZED, "user not found"));

        var bootcamp = mapper.map(createBootcampReqDto, Bootcamp.class);
        bootcamp.setUser(user);

        bootcamp = bootcampRepo.save(bootcamp);
        log.info("saved bootcamp -> " + bootcamp);

        var bootcampRes = mapper.map(bootcamp, BootcampResDto.class);
        return bootcampRes;
    }

    @Override
    public BootcampResDto updateBootcamp(String id, UpdateBootcampReqDto updateBootcampReqDto, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(id)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("bootcamp with id %s not found", id)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", id));
        }

        if(updateBootcampReqDto.getName() != null){
            if(bootcampRepo.findByName(updateBootcampReqDto.getName()).isEmpty()){
                throw new CampApiException(HttpStatus.NOT_ACCEPTABLE, "bootcamp with the name already exists");
            }
            bootcamp.setName(updateBootcampReqDto.getName());
        }

        if(updateBootcampReqDto.getDescription() != null){
            bootcamp.setDescription(updateBootcampReqDto.getDescription());
        }

        if(updateBootcampReqDto.getWebsite() != null){
            bootcamp.setWebsite(updateBootcampReqDto.getWebsite());
        }

        if(updateBootcampReqDto.getPhone() != null){
            bootcamp.setPhone(updateBootcampReqDto.getPhone());
        }

        if(updateBootcampReqDto.getEmail() != null){
            bootcamp.setEmail(updateBootcampReqDto.getEmail());
        }

        if(updateBootcampReqDto.getAddress() != null){
            Address address = mapper.map(updateBootcampReqDto.getAddress(), Address.class);
            bootcamp.setAddress(address);
        }

        if(updateBootcampReqDto.getCareers() != null && updateBootcampReqDto.getCareers().size() > 0){
            var acceptableCareer = validateCareer(updateBootcampReqDto.getCareers());
            bootcamp.setCareers(acceptableCareer);
        }

        if(updateBootcampReqDto.getHousing() != null){
            bootcamp.setHousing(updateBootcampReqDto.getHousing());
        }

        if(updateBootcampReqDto.getJobAssistance() != null){
            bootcamp.setJobAssistance(updateBootcampReqDto.getJobAssistance());
        }

        if(updateBootcampReqDto.getJobGuarantee() != null){
            bootcamp.setJobGuarantee(updateBootcampReqDto.getJobGuarantee());
        }

        if(updateBootcampReqDto.getAcceptGI() != null){
            bootcamp.setAcceptGI(updateBootcampReqDto.getAcceptGI());
        }

        bootcamp = bootcampRepo.save(bootcamp);

        return mapper.map(bootcamp, BootcampResDto.class);
    }

    List<String> validateCareer(List<String> requestCareers){
        List<String> availableCareer = careerService.getAllCareers()
                .stream().map(career -> career.name).toList();

        return requestCareers.stream().filter(availableCareer::contains)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteBootcamp(String id) {
        bootcampRepo.deleteById(id);
        return id;
    }
}
