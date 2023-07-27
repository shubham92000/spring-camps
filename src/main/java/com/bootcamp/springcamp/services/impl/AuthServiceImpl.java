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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginReqDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
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
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(List.of(role));

        user = userRepo.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(registerDto.getEmail());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenProvider.generateToken(authenticationToken);
        return token;
    }
}
