package com.pi.projet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCridentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Or another appropriate strategy

    private Long id ;

    private String userId;

    private String mail ;
}
