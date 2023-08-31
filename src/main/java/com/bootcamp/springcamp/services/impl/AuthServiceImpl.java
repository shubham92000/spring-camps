package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.auth.*;
import com.bootcamp.springcamp.dtos.login.LoginReqDto;
import com.bootcamp.springcamp.dtos.login.LoginResDto;
import com.bootcamp.springcamp.dtos.register.RegisterReqDto;
import com.bootcamp.springcamp.dtos.register.RegisterResDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.security.JwtTokenProvider;
import com.bootcamp.springcamp.services.AuthService;
import com.bootcamp.springcamp.services.EmailService;
import com.bootcamp.springcamp.utils.Role;
import com.bootcamp.springcamp.utils.RoleValue;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    public static Integer RESET_PASSWORD_TOKEN_EXPIRY = 30;
    private ModelMapper mapper;
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private MongoTemplate mongoTemplate;

    public AuthServiceImpl(ModelMapper mapper, AuthenticationManager authenticationManager, UserRepo userRepo, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, EmailService emailService, MongoTemplate mongoTemplate) {
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.mongoTemplate = mongoTemplate;
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

        return sendToken(user);
    }

    @Override
    public UserResDto getUser(Authentication authentication) {
        String email = authentication.getName();
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", email)));
        return mapper.map(user, UserResDto.class);
    }

    @Override
    public UserResDto updateDetails(UpdateDetailsReqDto updateDetails, Authentication authentication) {
        var user = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(updateDetails.getName() != null){
            user.setName(updateDetails.getName());
        }

        userRepo.save(user);

        return mapper.map(user, UserResDto.class);
    }

    @Override
    public RegisterResDto updatePassword(UpdatePasswordReqDto updatePassword, Authentication authentication) {
        var user = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!matchPasswords(user, updatePassword.getOldPassword())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, "invalid password");
        }

        user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));

        userRepo.save(user);

        return sendToken(user);
    }

    private boolean matchPasswords(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public ForgotPasswordResDto forgotPassword(ForgotPasswordReqDto forgotPasswordReqDto, HttpServletRequest request) {
        var user = userRepo.findByEmail(forgotPasswordReqDto.getEmail())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", forgotPasswordReqDto.getEmail())));

        String resetToken = user.generateResetPasswordToken();

        user = userRepo.save(user);

        String protocol = String.valueOf(request.getServerPort());
        String hostName = request.getServerName();

        String resetUrl = String.format("%s://%s/api/v1/auth/resetpassword/%s", protocol, hostName, resetToken);
        String message = "You are receiving this email because you (or someone else) has requested the rest of a password. Please ,make a PUT request to: \n \n " + resetUrl;

        try {
            emailService.sendEmail(user.getEmail(), "subject", message);
        } catch (Exception e) {
            user.setResetPasswordToken(null);
            user.setResetPasswordExpire(null);
            user = userRepo.save(user);

            throw new CampApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Email could not be sent");
        }

        return new ForgotPasswordResDto("email sent");
    }

    @Override
    public RegisterResDto resetPassword(ResetPasswordReqDto resetPasswordReqDto, String resetToken) {
        // fetch user with resetToken

        var user = mongoTemplate.findOne(Query.query(
                Criteria
                        .where("resetPasswordToken").is(resetToken)
                        .andOperator(Criteria.where("resetPasswordExpire").gt(LocalDateTime.now()))
        ), User.class, "users");

        if(user == null){
            throw new CampApiException(HttpStatus.BAD_REQUEST, "Invalid Token");
        }

        // set new password
        user.setPassword(resetPasswordReqDto.getPassword());
        user = userRepo.save(user);

        // send token
        return sendToken(user);
    }

    private RegisterResDto sendToken(User user){
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        String token = jwtTokenProvider.generateToken(authenticationToken);

        return new RegisterResDto(token);
    }
}
