package com.pi.users.controllers;

import com.pi.users.entities.Interest;
import com.pi.users.services.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/interests")
@CrossOrigin(origins = "http://localhost:4200")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @GetMapping("/inetrests")
    public ResponseEntity<List<Interest>> getAllInterests() {
        List<Interest> interests = interestService.getAllInterests();
        return new ResponseEntity<>(interests, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Interest> addInterest(@RequestBody Interest interest){
        return new ResponseEntity<>(interestService.add(interest),HttpStatus.OK);
    }
}
