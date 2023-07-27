package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.LoginReqDto;
import com.bootcamp.springcamp.dtos.LoginResDto;
import com.bootcamp.springcamp.dtos.RegisterReqDto;
import com.bootcamp.springcamp.dtos.RegisterResDto;
import com.bootcamp.springcamp.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginDto){
        String token = authService.login(loginDto);
        LoginResDto response = new LoginResDto(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<RegisterResDto> register(@RequestBody RegisterReqDto registerDto){
        String token = authService.register(registerDto);
        RegisterResDto response = new RegisterResDto(token);
        return ResponseEntity.ok(response);
    }
}
