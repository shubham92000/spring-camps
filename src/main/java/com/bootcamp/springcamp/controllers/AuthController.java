package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.auth.*;
import com.bootcamp.springcamp.dtos.login.LoginReqDto;
import com.bootcamp.springcamp.dtos.login.LoginResDto;
import com.bootcamp.springcamp.dtos.register.RegisterReqDto;
import com.bootcamp.springcamp.dtos.register.RegisterResDto;
import com.bootcamp.springcamp.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginDto){
        LoginResDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<RegisterResDto> register(@Valid @RequestBody RegisterReqDto registerDto){
        RegisterResDto response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResDto> getUser(Authentication authentication) {
        UserResDto response = authService.getUser(authentication);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatedetails")
    public ResponseEntity<UserResDto> updateDetails(@RequestBody @Valid UpdateDetailsReqDto updateDetails, Authentication authentication) {
        UserResDto response = authService.updateDetails(updateDetails, authentication);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<RegisterResDto> updatePassword(@RequestBody @Valid UpdatePasswordReqDto updatePassword, Authentication authentication) {
        RegisterResDto response = authService.updatePassword(updatePassword, authentication);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<ForgotPasswordResDto> forgotPassword(@RequestBody @Valid ForgotPasswordReqDto forgotPasswordReqDto, HttpServletRequest request) {
        ForgotPasswordResDto response = authService.forgotPassword(forgotPasswordReqDto, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/resetpassword/{resetToken}")
    public ResponseEntity<RegisterResDto> resetPassword(@RequestBody @Valid ResetPasswordReqDto resetPasswordReqDto,@PathVariable String resetToken) {
        RegisterResDto response = authService.resetPassword(resetPasswordReqDto, resetToken);
        return ResponseEntity.ok(response);
    }
}
