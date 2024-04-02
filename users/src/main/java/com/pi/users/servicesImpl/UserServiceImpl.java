package com.pi.users.servicesImpl;

import com.pi.users.entities.Role;
import com.pi.users.entities.Speciality;
import com.pi.users.entities.User;
import com.pi.users.repository.UserRepo;
import com.pi.users.services.UserServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private  UserRepo userRepo ;
    @Autowired
    private  PasswordEncoder passwordEncoder ;

    @Autowired
    private UserRepo userRepository;



  /*  @PostConstruct
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
            userRepository.save(adminAccount);
        }
            System.out.println("admin account created successfuly");

        }*/

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));


        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setLevel(userDetails.getLevel());
        user.setNumTel(userDetails.getNumTel());
        user.setRole(Role.STUDENT);
        user.setSpeciality(userDetails.getSpeciality());

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


    }





