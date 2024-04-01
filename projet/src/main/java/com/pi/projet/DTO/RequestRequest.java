package com.pi.projet.DTO;

import com.pi.projet.entities.Projet;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.pi.projet.entities.Category;
import com.pi.projet.entities.ProjectStatus;
import com.pi.projet.entities.Request;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestRequest {

    private Long encadreurId;


    private Long projectId;

    private String message;
}
