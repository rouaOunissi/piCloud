package com.pi.users.servicesImpl;


import com.netflix.discovery.converters.Auto;
import com.pi.users.email.EmailSender;
import com.pi.users.entities.User;
import com.pi.users.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DebloquerUser {
    @Autowired
    private UserRepo userRepository;


    @Scheduled(fixedRate = 120000)
    public void cleanupComments() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (!user.isEnabled()) {
                user.setEnabled(true);
                userRepository.save(user);
                System.out.println("User  " + user.getIdUser() + " has been debloqued  ");

            }
        }
    }

}
