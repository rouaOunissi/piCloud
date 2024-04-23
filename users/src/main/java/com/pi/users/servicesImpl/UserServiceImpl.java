package com.pi.users.servicesImpl;

import com.pi.users.email.EmailSender;
import com.pi.users.entities.Role;
import com.pi.users.entities.Speciality;
import com.pi.users.entities.User;
import com.pi.users.repository.UserRepo;
import com.pi.users.services.UserServices;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserServices {



    @Autowired
    private  UserRepo userRepo ;
    @Autowired
    private  PasswordEncoder passwordEncoder ;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private  EmailSender emailSender ;




    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepo.findByRole(Role.ADMIN);
        if(adminAccount==null) {
            adminAccount = new User();
            adminAccount.setFirstName("admin");
            adminAccount.setLastName("admin");
            adminAccount.setEmail("admin@gmail.com");
            adminAccount.setPassword(passwordEncoder.encode("admin"));
            adminAccount.setLevel(5);
            adminAccount.setNumTel(20369845);
            adminAccount.setRole(Role.ADMIN);
            adminAccount.setSpeciality(Speciality.ARCTIC);
            adminAccount.setEnabled(true);
            adminAccount.setRegistrationDate(LocalDate.now());

            userRepository.save(adminAccount);
        }
            log.info("admin account created successfuly");

        }

    @Override
    public User updateUser(Long id,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("level") int level,
                           @RequestParam("numTel") int numTel,
                           @RequestParam("speciality") Speciality speciality,
                           @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setLevel(level);
        user.setNumTel(numTel);

        user.setRole(Role.STUDENT);
        user.setSpeciality(speciality);

        if (image != null && !image.isEmpty()) {

            byte[] imageBytes = image.getBytes();
            user.setImage(imageBytes);
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> searchByFirstName(String firstName) {
        return userRepository.findByFirstNameContaining(firstName);
    }

    @Override
    public byte[] getUserImage(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return user.getImage();
    }

    @Override
    public Optional<String> findEmailById(Long id) {
        return userRepository.findEmailById(id);
    }

    @Override
    public Boolean checkUserEnabled(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::isEnabled).orElse(null);
    }

    @Override
    public String forgotPassword(String email) throws MessagingException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        emailSender.sendSetPasswordEmail(email);

        return "Please check your email to set a new password ";
    }

    @Override
    public String setPassword(String email, String newPassword) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String encryptedPassword =passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            userRepo.save(user);

            return "Password updated successfully.";
        } else {
            return "No user found with that email address.";
        }
    }

    @Override
    public List<Object[]> getUsersRegistrationStats() {
        return userRepository.countUsersByRegistrationMonth();
    }


}





