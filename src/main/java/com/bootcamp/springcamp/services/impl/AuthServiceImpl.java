package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.auth.UserResDto;
import com.bootcamp.springcamp.dtos.login.LoginReqDto;
import com.bootcamp.springcamp.dtos.login.LoginResDto;
import com.bootcamp.springcamp.dtos.register.RegisterReqDto;
import com.bootcamp.springcamp.dtos.register.RegisterResDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.security.JwtTokenProvider;
import com.bootcamp.springcamp.services.AuthService;
import com.bootcamp.springcamp.utils.Role;
import com.bootcamp.springcamp.utils.RoleValue;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private ModelMapper mapper;
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(ModelMapper mapper, AuthenticationManager authenticationManager, UserRepo userRepo, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResDto login(LoginReqDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return new LoginResDto(token);
    }

    @Override
    public RegisterResDto register(RegisterReqDto registerDto) {
        if(registerDto.getPassword().length() < 6){
            throw new CampApiException(HttpStatus.BAD_REQUEST, "invalid password");
        }

        if(userRepo.existsByEmail(registerDto.getEmail())){
            throw new CampApiException(HttpStatus.BAD_REQUEST, "email already exists");
        }

        Role role = RoleValue.getRole(registerDto.getUserType());
        if(role == null || role == Role.ROLE_ADMIN) {
            throw new CampApiException(HttpStatus.BAD_REQUEST, "invalid userType");
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
        return new RegisterResDto(token);
    }

    @Override
    public UserResDto getUser(Authentication authentication) {
        String email = authentication.getName();
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", email)));
        return mapper.map(user, UserResDto.class);
    }
}
