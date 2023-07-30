package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.course.CourseResDto;
import com.bootcamp.springcamp.dtos.course.CreateCourseReqDto;
import com.bootcamp.springcamp.dtos.course.UpdateCourseReqDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Course;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.CourseRepo;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.services.CourseService;
import com.bootcamp.springcamp.utils.Skill;
import com.bootcamp.springcamp.utils.SkillValue;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    private ModelMapper mapper;
    private CourseRepo courseRepo;
    private BootcampRepo bootcampRepo;
    private UserRepo userRepo;

    public CourseServiceImpl(ModelMapper mapper, CourseRepo courseRepo, BootcampRepo bootcampRepo, UserRepo userRepo) {
        this.mapper = mapper;
        this.courseRepo = courseRepo;
        this.bootcampRepo = bootcampRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<CourseResDto> getAllCoursesOfBootcamp(String bootcampId) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not found", bootcampId)));
        var courses = courseRepo.findByBootcamp(bootcamp);
//        var courses = bootcamp.courses;
        return courses.stream().map(course -> mapper.map(course, CourseResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseResDto getCourse(String courseId) {
        var course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("course with id %s not found", courseId)));
        log.info("courses: "+course);
        return mapper.map(course, CourseResDto.class);
    }

    @Override
    public CourseResDto createCourse(CreateCourseReqDto createCourseReqDto, Authentication authentication) {
        var email = authentication.getName();
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", email)));

        var bootcamp = bootcampRepo.findById(createCourseReqDto.getBootcampId())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not found", createCourseReqDto.getBootcampId())));

        Skill skill = SkillValue.getSkill(createCourseReqDto.getSkillType());
        if(skill == null){
            throw new CampApiException(HttpStatus.NOT_ACCEPTABLE, "skill type is invalid");
        }

        var course = new Course();
        course.setTitle(createCourseReqDto.getTitle());
        course.setDescription(createCourseReqDto.getDescription());
        course.setWeeks(createCourseReqDto.getWeeks());
        course.setTuition(createCourseReqDto.getTuition());
        course.setMinimumSkill(skill);
        course.setScholarshipAvailable(
                createCourseReqDto.getScholarshipAvailable() != null
                ? createCourseReqDto.getScholarshipAvailable() : false
        );
        course.setBootcamp(bootcamp);
        course.setUser(user);

        course = courseRepo.save(course);
        log.info("courses: "+course);

        return mapper.map(course, CourseResDto.class);
    }

    @Override
    public CourseResDto updateCourse(String courseId, UpdateCourseReqDto courseInfo, Authentication authentication) {
        var course = courseRepo.findById(courseId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("course with id %s not found", courseId)));

        var courseCreator = course.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", authentication.getName())));

        if(!courseCreator.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update course with %s", courseId));
        }

        if(courseInfo.getTitle() != null){
            var courseWithSameNameInBootcamp = courseRepo.findByTitleAndBootcamp(courseInfo.getTitle(), course.getBootcamp()).isPresent();
            if(courseWithSameNameInBootcamp){
                throw new CampApiException(HttpStatus.NOT_ACCEPTABLE, "course with the title already exists");
            }
            course.setTitle(courseInfo.getTitle());
        }

        if(courseInfo.getDescription() != null){
            course.setDescription(courseInfo.getDescription());
        }

        if(courseInfo.getWeeks() != null){
            course.setWeeks(courseInfo.getWeeks());
        }

        if(courseInfo.getTuition() != null){
            course.setTuition(courseInfo.getTuition());
        }

        if(courseInfo.getMinimumSkill() != null){
            course.setMinimumSkill(courseInfo.getMinimumSkill());
        }

        if(courseInfo.getScholarshipAvailable() != null){
            course.setScholarshipAvailable(courseInfo.getScholarshipAvailable());
        }

        course = courseRepo.save(course);
        return mapper.map(course, CourseResDto.class);
    }

    @Override
    public String deleteCourse(String courseId) {
        courseRepo.deleteById(courseId);
        return courseId;
    }
}
