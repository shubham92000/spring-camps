package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.auth.UserResDto;
import com.bootcamp.springcamp.dtos.login.LoginReqDto;
import com.bootcamp.springcamp.dtos.login.LoginResDto;
import com.bootcamp.springcamp.dtos.register.RegisterReqDto;
import com.bootcamp.springcamp.dtos.register.RegisterResDto;
import com.bootcamp.springcamp.services.AuthService;
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
}
