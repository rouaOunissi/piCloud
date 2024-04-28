package com.pi.ressources.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi.ressources.Enum.TypeRessource;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Data

@NoArgsConstructor

public class Ressource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRessource;


    private String titre;

    private String description;
    private Long nbrReact;
    private String urlFile;


    private String fileName;

    private String fileType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Enumerated(EnumType.STRING)
    private TypeRessource TypeR;

    private Long idUser ;

    @OneToMany(mappedBy = "ressource", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Download> downloads = new ArrayList<>();

    @OneToMany(mappedBy = "ressource", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reaction> reactions = new ArrayList<>();

}
