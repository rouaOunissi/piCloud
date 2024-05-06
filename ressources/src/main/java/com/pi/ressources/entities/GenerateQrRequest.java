package com.pi.ressources.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GenerateQrRequest {
    @Id
    private Long idGenerateQrRequest;
    private String qrString;

}
