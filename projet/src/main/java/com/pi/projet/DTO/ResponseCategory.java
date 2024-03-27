package com.pi.projet.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCategory {

    private Long id ;
    private String name ;
    private int nbr_projects;
}
