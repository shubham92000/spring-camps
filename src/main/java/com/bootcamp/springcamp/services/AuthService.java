package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.auth.*;
import com.bootcamp.springcamp.dtos.login.LoginReqDto;
import com.bootcamp.springcamp.dtos.login.LoginResDto;
import com.bootcamp.springcamp.dtos.register.RegisterReqDto;
import com.bootcamp.springcamp.dtos.register.RegisterResDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginResDto login(LoginReqDto loginDto);
    RegisterResDto register(RegisterReqDto registerDto);
    UserResDto getUser(Authentication authentication);
    UserResDto updateDetails(UpdateDetailsReqDto updateDetails, Authentication authentication);
    RegisterResDto updatePassword(UpdatePasswordReqDto updatePassword, Authentication authentication);
    ForgotPasswordResDto forgotPassword(ForgotPasswordReqDto forgotPasswordReqDto, HttpServletRequest request);
    RegisterResDto resetPassword(ResetPasswordReqDto resetPasswordReqDto, String resetToken);
}
