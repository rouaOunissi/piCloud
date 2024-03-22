package com.pi.cours.services;

import com.pi.cours.dao.CourseDao;
import lombok.extern.slf4j.Slf4j;
import com.pi.cours.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public List<Course> getAllCours() {
        return courseDao.findAll();
    }
    @Autowired
    private FileService fileService;

    @Override
    public Course getCoursById(Long id) {
        Optional<Course> optionalCours = courseDao.findById(id);
        return optionalCours.orElse(null);
    }

    @Override
    public Course createCours(Course cours) {
        return courseDao.save(cours);
    }

    @Override
    public Course updateCours(Long id, Course cours) {
        if (courseDao.existsById(id)) {
            cours.setId(id);
            return courseDao.save(cours);
        }
        return null; // Handle not found scenario as per your requirement
    }

    @Override
    public void deleteCours(Long id) {
        courseDao.deleteById(id);
    }
    @Override
    public Optional<Course> addCoursWithFile(Course course, MultipartFile file) {
        try {
            String filePath = fileService.uploadFile(file).orElse(null);
            if (filePath != null) {
                course.setFilePath(filePath);
                Course savedCourse = courseDao.save(course);
                return Optional.of(savedCourse);
            }
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}