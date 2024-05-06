package com.pi.cours.services;

import com.pi.cours.models.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CourseService {


    Course addCourse(Course course, MultipartFile[] files);

    Course updateCourse(Course course);
    void deleteCourse(Long id);
    Course getCourseById(Long id);
    List<Course> getAllCourses();

    Optional<Course> findCourseByTitle(String title);
}
