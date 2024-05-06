package com.pi.users.services;


import com.pi.users.Dto.UserInterestDTO;
import com.pi.users.entities.Speciality;
import com.pi.users.entities.User;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserServices {


     User updateUser(Long id,
                     @RequestParam("firstName") String firstName,
                     @RequestParam("lastName") String lastName,
                     @RequestParam("email") String email,
                     @RequestParam("password") String password,
                     @RequestParam("level") int level,
                     @RequestParam("numTel") int numTel,
                     @RequestParam("speciality") Speciality speciality,
                     @RequestParam(value = "image", required = false) MultipartFile image) throws IOException;

     void deleteUser(Long id) ;

     User getUserById(Long id);

     List<User> findAll() ;
     List<User> searchByFirstName(String firstName);

     byte[] getUserImage(Long id) ;

     public Optional<String> findEmailById(Long id);
     public Boolean checkUserEnabled(String email);


    String forgotPassword(String email) throws MessagingException;

     String setPassword(String email, String newPassword);
    public List<Object[]> getUsersRegistrationStats();

    public User updateUserImage(Long userId, MultipartFile imageFile) throws IOException;
    public List<User> getUsersBySpeciality(Speciality speciality);


    void setUserStatus(long idUser);
}
