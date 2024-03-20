package com.pi.ressources.entities;

import com.pi.ressources.Enum.TypeRessource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Entity
public class Ressource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRessource;

    private String titre;
    private String description;
    private Long nbrReact;
    private String urlFile;

    @Enumerated(EnumType.STRING)
    private TypeRessource TypeR;
    private Long idUser ;


}
