package com.pi.projet.DTO;

import com.pi.projet.entities.RequestStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRequest2 {

    private Long id ;
    private String projetTitle;
    private  String message ;
    private RequestStatus status ;
}
