package com.pi.cours.services;

import com.pi.cours.models.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    List<Course> getAllCours();
    Course getCoursById(Long id);
    Course createCours(Course cours);
    Course updateCours(Long id, Course cours);
    void deleteCours(Long id);


    void addCoursWithFile(String title, String description, MultipartFile video, BigDecimal price) throws ParseException, IOException;
}
