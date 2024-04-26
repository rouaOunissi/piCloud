package com.pi.projet.repositories;

import com.pi.projet.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Methods to fetch notifications for a user
}
