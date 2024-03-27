package org.example.purchase.controllers;


import com.pi.cours.models.Course;
import com.pi.users.entities.User;
import org.example.purchase.externalApi.CourseApi;
import org.example.purchase.externalApi.UserApi;
import org.example.purchase.services.PurchaseImp;
import org.example.purchase.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@CrossOrigin
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    UserApi userApi ;

    @Autowired
    CourseApi courseApi;

    @PostMapping("/create/{userId}/{courseId}")
    public ResponseEntity<String> createPurchase(@PathVariable Long userId, @PathVariable Long courseId) {
        purchaseService.createPurchase(userId, courseId);
        return ResponseEntity.ok("added");
    }


    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userApi.getUserById(id);
    }


    @GetMapping("/cours/{id}")
    public Course getCoursById(@PathVariable Long id) {
        return courseApi.getCoursById(id);
    }
}

