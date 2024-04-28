package com.pi.ressources.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReaction;

    @ManyToOne
    @JoinColumn(name = "ressource_id")
    private Ressource ressource;

    private Long idUser ;


    public Reaction() {

    }
}
