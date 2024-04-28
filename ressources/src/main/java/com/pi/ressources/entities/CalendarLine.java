package com.pi.ressources.entities;

import com.pi.ressources.Enum.TypeRessource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.pi.ressources.Enum.Specialite;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Getter
@Setter
@Entity
public class CalendarLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalendarLine;

    private String urlFile;


    private String fileName;

    private String fileType;

    private String anneeUniv;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;


    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    private Long idUser ;

}
