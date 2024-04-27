package com.pi.projet.DTO;

import com.pi.projet.entities.RequestStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRequest2 {

    private String projetTitle;
    private RequestStatus status ;
}
