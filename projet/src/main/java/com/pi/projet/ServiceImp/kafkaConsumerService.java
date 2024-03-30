package com.pi.projet.ServiceImp;

import com.pi.projet.entities.UserCridentials;
import com.pi.projet.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class kafkaConsumerService {

    private final UserRepo userRepo ;


    @KafkaListener(topics = "mohamed", groupId = "mohamed")
    public void listenUserSignupEvent(String message) {
        String[] parts = message.split(",");
        String userId = parts[0];
        String email = parts[1];
        // Stockez ces informations dans votre base de données
        UserCridentials user = UserCridentials.builder()
                .userId(userId)
                .mail(email)
                .build();

        userRepo.save(user);

    }
}
