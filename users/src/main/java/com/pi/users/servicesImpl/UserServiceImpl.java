package com.pi.users.servicesImpl;

import com.pi.users.entities.User;
import com.pi.users.repository.UserRepo;
import com.pi.users.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private  UserRepo userRepo ;
    @Autowired
    private  PasswordEncoder passwordEncoder ;

    @Autowired
    private UserRepo userRepository;
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
        user.setRole(userDetails.getRole());
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
    public void addUser(User user) {
        userRepository.save(user);
    }


}
