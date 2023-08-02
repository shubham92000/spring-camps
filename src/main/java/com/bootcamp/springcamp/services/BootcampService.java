package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.bootcamp.BootcampResDto;
import com.bootcamp.springcamp.dtos.bootcamp.CreateBootcampReqDto;
import com.bootcamp.springcamp.dtos.bootcamp.UpdateBootcampReqDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BootcampService {
    public List<BootcampResDto> getAllBootcamps();
    public BootcampResDto getBootcamp(String id);
    public BootcampResDto createBootcamp(CreateBootcampReqDto createBootcampReqDto, Authentication authentication);
    public BootcampResDto updateBootcamp(String id, UpdateBootcampReqDto updateBootcampReqDto, Authentication authentication);
    public String deleteBootcamp(String id);
    public String uploadFile(String bootcampId, MultipartFile file, Authentication authentication);
}
