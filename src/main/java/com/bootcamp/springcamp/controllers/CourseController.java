package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.course.CourseResDto;
import com.bootcamp.springcamp.dtos.course.CreateCourseReqDto;
import com.bootcamp.springcamp.dtos.course.UpdateCourseReqDto;
import com.bootcamp.springcamp.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/bootcamp/{id}")
    public ResponseEntity<List<CourseResDto>> getAllCoursesOfBootcamp(@PathVariable(name = "id") String bootcampId){
        var res = courseService.getAllCoursesOfBootcamp(bootcampId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResDto> getCourse(@PathVariable(name = "id") String courseId){
        var res = courseService.getCourse(courseId);
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_PUBLISHER", "ROLE_ADMIN" })
    @PostMapping
    public ResponseEntity<CourseResDto> createCourse(@Valid @RequestBody CreateCourseReqDto createCourseReqDto, Authentication authentication){
        var res = courseService.createCourse(createCourseReqDto, authentication);
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_PUBLISHER", "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<CourseResDto> updateCourse(@PathVariable(name = "id") String courseId, @Valid @RequestBody UpdateCourseReqDto courseInfo){
        var res = courseService.updateCourse(courseId, courseInfo);
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_PUBLISHER", "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(String courseId){
        var res = courseService.deleteCourse(courseId);
        return ResponseEntity.ok(res);
    }
}
