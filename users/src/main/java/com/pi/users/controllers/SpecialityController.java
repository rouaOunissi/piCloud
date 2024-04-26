package com.pi.users.controllers;

import com.pi.users.entities.Speciality;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/specialities")
@CrossOrigin(origins = "http://localhost:4200")
public class SpecialityController {

    @GetMapping("/all")
    public Speciality[] getAllSpecialities() {
        return Speciality.values();
    }
}
