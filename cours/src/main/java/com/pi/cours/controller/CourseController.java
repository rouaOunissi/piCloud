package com.pi.cours.controller;

import com.pi.cours.dao.CourseDao;
import com.pi.cours.services.FileService;
import lombok.extern.slf4j.Slf4j;
import com.pi.cours.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pi.cours.services.CourseService;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/cours/")


@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")

public class CourseController {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseService courseService;
    @Autowired
    private FileService fileService;



    @GetMapping("/{courseId}/video")
    public ResponseEntity<byte[]> getCourseVideo(@PathVariable Long courseId) {
        Optional<Course> courseOptional = courseDao.findById(courseId);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            byte[] videoContent = course.getFileContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", "video.mp4");

            return new ResponseEntity<>(videoContent, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public List<Course> getAllCours() {
        return courseService.getAllCours();
    }

        @GetMapping("/test")
        public String test() {
            return "test";
        }



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


   /* @PostMapping("/add")
    public ResponseEntity<?> addCoursWithFile(@RequestBody Course cours, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.uploadFile(file).orElse(null);
            if (filePath != null) {
                cours.setFilePath(filePath);
                Course savedCours = courseService.createCours(cours);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedCours);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload file");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }*/
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoursWithFile(@RequestParam("title") String title, @RequestParam("description") String description,
                          @RequestParam("video") MultipartFile video, @RequestParam("price") BigDecimal price) throws ParseException, IOException {
        this.courseService.addCoursWithFile(title,description,video,price);
    }



    @GetMapping("/qq")
    public String tes2t() {
        return "test3";
    }
}
