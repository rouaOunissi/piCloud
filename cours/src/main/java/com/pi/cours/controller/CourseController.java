package com.pi.cours.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.cours.dao.CourseDao;
import com.pi.cours.models.Video;
import com.pi.cours.services.CourseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.pi.cours.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pi.cours.services.CourseService;
import org.springframework.web.multipart.MultipartFile;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/cours/")


@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class CourseController {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseService courseService;



    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Course> addCourse(@RequestParam("course") String courseStr, @RequestParam(value = "files", required = false) MultipartFile[] files) {
        try {
            Course course = new ObjectMapper().readValue(courseStr, Course.class);
            Course savedCourse = courseService.addCourse(course, files);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        try {
            Course existingCourse = courseService.getCourseById(id);
            existingCourse.setTitle(course.getTitle());
            existingCourse.setDescription(course.getDescription());
            existingCourse.setPrice(course.getPrice());
            // Assuming other fields like videoPath and userId might not be updated directly here
            Course updatedCourse = courseService.updateCourse(existingCourse);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/videos/{filename}")
    public ResponseEntity<org.springframework.core.io.Resource> serveVideo(@PathVariable Long id, @PathVariable String filename) {
        Path filePath = Paths.get("D:\\ArcTic2\\piCloudAngularfinal\\uploads", filename);
        try {
            Resource video = new UrlResource(filePath.toUri());
            if (video.exists() || video.isReadable()) {
                return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + video.getFilename() + "\"")
                        .body(video);
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<Course> getCourseByTitle(@PathVariable String title) {
        Optional<Course> course = courseService.findCourseByTitle(title);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/videos")
    public ResponseEntity<List<Video>> getCourseVideos(@PathVariable Long id) {
        Optional<Course> courseOptional = Optional.ofNullable(courseService.getCourseById(id));
        if (courseOptional.isPresent()) {
            List<Video> videos = new ArrayList<>(courseOptional.get().getVideos());
            return ResponseEntity.ok(videos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/qq")
    public String tes2t() {
        return "test3";
    }
}
 /* @GetMapping("/{courseId}/video")
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
    */