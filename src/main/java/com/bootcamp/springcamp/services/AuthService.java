package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.LoginReqDto;
import com.bootcamp.springcamp.dtos.RegisterReqDto;

public interface AuthService {
    String login(LoginReqDto loginDto);
    String register(RegisterReqDto registerDto);
}
