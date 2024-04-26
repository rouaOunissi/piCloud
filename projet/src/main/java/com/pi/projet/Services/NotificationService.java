package com.pi.projet.Services;

import com.pi.projet.entities.Notification;

public interface NotificationService {

    void sendApplicationStatusNotification(Long userId, String message);

}
