package com.bootcamp.springcamp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('USER')")
public class BootcampController {
    @GetMapping("/test")
    public String test(Authentication authentication){
        return "ok";
    }
}
