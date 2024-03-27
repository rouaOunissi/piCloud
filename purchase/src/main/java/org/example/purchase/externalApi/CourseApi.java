package org.example.purchase.externalApi;


import com.pi.cours.models.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "courseApi",url = "http://localhost:8020")
public interface CourseApi {

@GetMapping("/api/v1/cours/{id}")
    public Course getCoursById (@PathVariable Long id);
}
