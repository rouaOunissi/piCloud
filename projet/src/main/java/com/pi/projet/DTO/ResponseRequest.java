package com.pi.projet.DTO;

import com.pi.projet.entities.Projet;
import com.pi.projet.entities.RequestStatus;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRequest {


    private Long id;

    private Long encadreurId;


    private String message;

    private RequestStatus status ;
}
