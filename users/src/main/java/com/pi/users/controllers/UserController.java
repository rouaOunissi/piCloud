package com.pi.users.controllers;

import com.pi.users.entities.Speciality;
import com.pi.users.entities.User;
import com.pi.users.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserServices userService ;

    @GetMapping
    public List<User> findAll(){
        return this.userService.findAll();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("level") int level,
            @RequestParam("numTel") int numTel,
            @RequestParam("speciality") Speciality speciality,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        User user = userService.getUserById(id);


        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setLevel(level);
        user.setNumTel(numTel);
        user.setSpeciality(speciality);

        if (image != null && !image.isEmpty()) {
            user.setImage(image.getBytes());
        }

        User updatedUser = userService.updateUser(id, firstName,lastName,email,password,level,numTel,speciality,image);
        return ResponseEntity.ok(updatedUser);


    }



    @GetMapping("/user/{idd}")
    public User getUserById(@PathVariable Long idd) {
        return userService.getUserById(idd);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String firstName) {
        return userService.searchByFirstName(firstName);
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        byte[] imageBytes = user.getImage();
        String contentType = "image/png";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageBytes);
    }




}
