package com.pi.users.controllers;

import com.pi.users.Dto.UserInterestDTO;
import com.pi.users.entities.Speciality;
import com.pi.users.entities.User;
import com.pi.users.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;


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



    @GetMapping("/{id}/image")
    public ResponseEntity<?> getUserImage(@PathVariable Long id) {
        try {
            byte[] imageData = userService.getUserImage(id);
            ByteArrayResource imageResource = new ByteArrayResource(imageData);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageResource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user-registration-stats")
    public List<Object[]> getUserRegistrationStats() {
        return userService.getUsersRegistrationStats();
    }


    @PutMapping("/{id}/image")
    public ResponseEntity<?> uploadUserImage(@PathVariable Long id,
                                             @RequestParam("image") MultipartFile image) {
        try {
            User updatedUser = userService.updateUserImage(id, image);
            return ResponseEntity.ok("Image updated successfully for user with ID: " + updatedUser.getIdUser());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save image", e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/users/by-speciality")
    public List<User> getUsersBySpeciality(@RequestParam Speciality speciality) {
        return userService.getUsersBySpeciality(speciality);
    }















}
