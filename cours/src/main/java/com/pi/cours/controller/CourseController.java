package com.pi.cours.controller;

import com.pi.cours.services.FileService;
import lombok.extern.slf4j.Slf4j;
import com.pi.cours.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pi.cours.services.CourseService;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/cours/")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FileService fileService;

    @GetMapping
    public List<Course> getAllCours() {
        return courseService.getAllCours();
    }

        @GetMapping("/test")
        public String test() {
            return "test";
        }

   /*@PostMapping("/add")
    public ResponseEntity<Course> createCours(@RequestBody Course cours) {
        Course createdCours = courseService.save(cours);
        return new ResponseEntity<>(createdCours, HttpStatus.CREATED);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCoursById(@PathVariable("id") Long id) {
        Course cours = courseService.getCoursById(id);
        if (cours != null) {
            return new ResponseEntity<>(cours, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCours(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   @PostMapping("/create")
    public ResponseEntity<Course> createCours(@RequestBody Course cours) {
        Course createdCours = courseService.createCours(cours);
        return new ResponseEntity<>(createdCours, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCours(@PathVariable("id") Long id, @RequestBody Course cours) {
        Course updatedCours = courseService.updateCours(id, cours);
        if (updatedCours != null) {
            return new ResponseEntity<>(updatedCours, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile( @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.uploadFile(file).orElse(null);
            if (filePath != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(filePath);

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload file");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }




    @GetMapping("/qq")
    public String tes2t() {
        return "test3";
    }

}
