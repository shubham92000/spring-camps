package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.bootcamp.BootcampResDto;
import com.bootcamp.springcamp.dtos.bootcamp.CreateBootcampReqDto;
import com.bootcamp.springcamp.dtos.bootcamp.UpdateBootcampReqDto;
import com.bootcamp.springcamp.services.BootcampService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bootcamps")
public class BootcampController {
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/test")
//    public String test(Authentication authentication){
//        return "ok";
//    }

    private final Logger log = LoggerFactory.getLogger(BootcampController.class);
    private BootcampService bootcampService;

    public BootcampController(BootcampService bootcampService) {
        this.bootcampService = bootcampService;
    }

    @GetMapping
    public ResponseEntity<List<BootcampResDto>> getAllBootcamps(){
        var response = bootcampService.getAllBootcamps();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BootcampResDto> getBootcamp(@PathVariable(name = "id") String id){
        var response = bootcampService.getBootcamp(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PUBLISHER')")
    @PostMapping
    public ResponseEntity<BootcampResDto> createBootcamp(@Valid @RequestBody CreateBootcampReqDto createBootcampReqDto, Authentication authentication){
        var response = bootcampService.createBootcamp(createBootcampReqDto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping("/{id}")
    public ResponseEntity<BootcampResDto> updateBootcamp(@PathVariable(name = "id") String id, UpdateBootcampReqDto updateBootcampReqDto){
        var response = bootcampService.updateBootcamp(updateBootcampReqDto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBootcamp(@PathVariable(name = "id") String id){
        var response = bootcampService.deleteBootcamp(id);
        return ResponseEntity.ok(response);
    }
}
