package com.pi.projet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long encadreurId; // ID of the user who wants to be the mentor

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Projet project;

    private String message; // Optional message from the encadreur

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
}
