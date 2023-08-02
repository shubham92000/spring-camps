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
    public BootcampResDto uploadPhotoForBootcamp(String bootcampId, MultipartFile file, Authentication authentication);
    public BootcampResDto deletePhotoForBootcamp(String bootcampId, String photoId, Authentication authentication);
    public BootcampResDto addEmail(String bootcampId, String email, Authentication authentication);
    public BootcampResDto deleteEmail(String bootcampId, String email, Authentication authentication);
    public BootcampResDto addPhone(String bootcampId, String phone, Authentication authentication);
    public BootcampResDto deletePhone(String bootcampId, String phone, Authentication authentication);
}
