package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.LoginReqDto;
import com.bootcamp.springcamp.dtos.RegisterReqDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.security.JwtTokenProvider;
import com.bootcamp.springcamp.services.AuthService;
import com.bootcamp.springcamp.utils.Role;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepo userRepo;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepo userRepo, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginReqDto loginDto) {
        return null;
    }

    @Override
    public String register(RegisterReqDto registerDto) {
        if(userRepo.existsByEmail(registerDto.getEmail())){
            throw new CampApiException(HttpStatus.BAD_REQUEST, "email already exists");
        }

        Role role = null;
        try {
            role = Role.valueOf(registerDto.getUserType());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("user type not found");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setRoles(List.of(role));

        user = userRepo.save(user);
        System.out.println(user);

        return "token";
    }
}
