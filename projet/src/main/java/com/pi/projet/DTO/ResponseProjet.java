package com.pi.projet.DTO;


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
public class ResponseProjet {



    private String title;


    private String description;

    private LocalDate creationDate;

    private ProjectStatus status = ProjectStatus.OPEN;




}
