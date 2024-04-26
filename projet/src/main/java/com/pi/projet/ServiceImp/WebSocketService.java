package com.pi.projet.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }



    public void sendNotification(String userId, String message) {
        // The destination path should be in sync with what the client subscribes to
        // The following is a common pattern: "/user/{userId}/queue/notifications"
        String destination = "/queue/notifications/" + userId;
        messagingTemplate.convertAndSendToUser(
                userId,
                destination,
                message
        );
    }
}
