package com.bootcamp.springcamp.services;

import com.bootcamp.springcamp.dtos.course.CourseResDto;
import com.bootcamp.springcamp.dtos.course.CreateCourseReqDto;
import com.bootcamp.springcamp.dtos.course.UpdateCourseReqDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CourseService {
    List<CourseResDto> getAllCoursesOfBootcamp(String bootcampId);
    CourseResDto getCourse(String courseId);
    CourseResDto createCourse(CreateCourseReqDto createCourseReqDto, Authentication authentication);
    CourseResDto updateCourse(String courseId, UpdateCourseReqDto courseInfo);
    String deleteCourse(String courseId);
}
