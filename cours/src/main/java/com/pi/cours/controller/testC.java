/*package com.pi.cours.controller;

import com.pi.cours.dao.CourseDao;
import com.pi.cours.models.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*@Slf4j
@RestController
@RequestMapping("/api/")

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")

/*public class testC {
    @Autowired
    private CourseDao courseDao;
    @GetMapping("/courses/{courseId}/video")
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

}*/
