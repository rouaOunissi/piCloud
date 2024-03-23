package com.pi.ressources.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity

public class Download implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDownload;

    @ManyToOne
    @JoinColumn(name = "id_ressource", nullable = false)
    private Ressource ressource;


    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDownload;


    private Long idUser ;

}
