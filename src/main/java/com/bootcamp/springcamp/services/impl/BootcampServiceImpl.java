package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.controllers.BootcampController;
import com.bootcamp.springcamp.dtos.bootcamp.BootcampResDto;
import com.bootcamp.springcamp.dtos.bootcamp.CreateBootcampReqDto;
import com.bootcamp.springcamp.dtos.bootcamp.UpdateBootcampReqDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.services.BootcampService;
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
    private BootcampRepo bootcampRepo;
    private UserRepo userRepo;

    public BootcampServiceImpl(BootcampRepo bootcampRepo, UserRepo userRepo) {
        this.bootcampRepo = bootcampRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<BootcampResDto> getAllBootcamps() {
        var bootcamps = bootcampRepo.findAll()
                .stream().map(bootcamp ->
                        BootcampResDto.builder()
                                .id(bootcamp.getId())
                                .name(bootcamp.getName())
                                .slug(null)
                                .description(bootcamp.getDescription())
                                .website(bootcamp.getWebsite())
                                .phone(bootcamp.getPhone())
                                .email(bootcamp.getEmail())
                                .formattedAddress(null)
                                .careers(bootcamp.getCareers())
                                .ratings(bootcamp.getRatings())
                                .costs(bootcamp.getCosts())
                                .photo(null)
                                .housing(bootcamp.getHousing())
                                .jobAssistance(bootcamp.getJobAssistance())
                                .jobGuarantee(bootcamp.getJobGuarantee())
                                .acceptGI(bootcamp.getAcceptGI())
                                .build()
                    )
                .collect(Collectors.toList());
        return bootcamps;
    }

    @Override
    public BootcampResDto getBootcamp(String id) {
        var bootcamp = bootcampRepo.findById(id)
                .orElseThrow(() -> new CampApiException(HttpStatus.OK, String.format("bootcamp with id %s not found", id)));

        var bootcampRes = BootcampResDto.builder()
                .id(bootcamp.getId())
                .name(bootcamp.getName())
                .slug(null)
                .description(bootcamp.getDescription())
                .website(bootcamp.getWebsite())
                .phone(bootcamp.getPhone())
                .email(bootcamp.getEmail())
                .formattedAddress(null)
                .careers(bootcamp.getCareers())
                .ratings(bootcamp.getRatings())
                .costs(bootcamp.getCosts())
                .photo(null)
                .housing(bootcamp.getHousing())
                .jobAssistance(bootcamp.getJobAssistance())
                .jobGuarantee(bootcamp.getJobGuarantee())
                .acceptGI(bootcamp.getAcceptGI())
                .build();

        return bootcampRes;
    }

    @Override
    public BootcampResDto createBootcamp(CreateBootcampReqDto createBootcampReqDto, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new CampApiException(HttpStatus.UNAUTHORIZED, "user not found"));

        var bootcamp = new Bootcamp();
        bootcamp.setName(createBootcampReqDto.getName());
        bootcamp.setDescription(createBootcampReqDto.getDescription());
        bootcamp.setWebsite(createBootcampReqDto.getWebsite());
        bootcamp.setPhone(createBootcampReqDto.getPhone());
        bootcamp.setEmail(createBootcampReqDto.getEmail());
        bootcamp.setUser(user);

        bootcamp = bootcampRepo.save(bootcamp);

        var bootcampRes = BootcampResDto.builder()
                .id(bootcamp.getId())
                .name(bootcamp.getName())
                .slug(null)
                .description(bootcamp.getDescription())
                .website(bootcamp.getWebsite())
                .phone(bootcamp.getPhone())
                .email(bootcamp.getEmail())
                .formattedAddress(null)
                .careers(bootcamp.getCareers())
                .ratings(bootcamp.getRatings())
                .costs(bootcamp.getCosts())
                .photo(null)
                .housing(bootcamp.getHousing())
                .jobAssistance(bootcamp.getJobAssistance())
                .jobGuarantee(bootcamp.getJobGuarantee())
                .acceptGI(bootcamp.getAcceptGI())
                .build();

        return bootcampRes;
    }

    @Override
    public BootcampResDto updateBootcamp(UpdateBootcampReqDto updateBootcampReqDto) {
        return null;
    }

    @Override
    public String deleteBootcamp(String id) {
        bootcampRepo.deleteById(id);
        return id;
    }
}
