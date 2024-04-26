package com.pi.projet.ServiceImp;

import com.pi.projet.Services.NotificationService;
import com.pi.projet.entities.Notification;
import com.pi.projet.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {


    @Autowired
    private NotificationRepository notificationRepository; // This should be the repository

    @Autowired
    private WebSocketService webSocketService;

    @Override
    @Transactional
    public void sendApplicationStatusNotification(Long userId, String message) {
        // Create and save the notification entity
        Notification notification = new Notification();
        notification.setRecipientId(userId);
        notification.setMessage(message);
        notification.setRead(false);
        notificationRepository.save(notification); // Save it using the repository

        // Send the notification via WebSocket
        // The destination should be where the user is subscribing to
        webSocketService.sendNotification(userId.toString(), message);
    }

}
