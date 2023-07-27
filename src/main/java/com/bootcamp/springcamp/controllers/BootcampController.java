package com.bootcamp.springcamp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootcampController {
    @GetMapping("/test")
    public String test(){
        return "ok";
    }
}
