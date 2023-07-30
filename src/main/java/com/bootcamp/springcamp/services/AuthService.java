package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.auth.UserResDto;
import com.bootcamp.springcamp.dtos.login.LoginReqDto;
import com.bootcamp.springcamp.dtos.login.LoginResDto;
import com.bootcamp.springcamp.dtos.register.RegisterReqDto;
import com.bootcamp.springcamp.dtos.register.RegisterResDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginResDto login(LoginReqDto loginDto);
    RegisterResDto register(RegisterReqDto registerDto);
    UserResDto getUser(Authentication authentication);
}
