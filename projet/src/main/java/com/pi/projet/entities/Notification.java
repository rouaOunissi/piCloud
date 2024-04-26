package com.pi.projet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long recipientId; // The user ID of the notification recipient

    @Column(nullable = false)
    private String message; // The message content of the notification

    @Column(nullable = false)
    private boolean isRead = false; // Flag to check if notification has been read

}
