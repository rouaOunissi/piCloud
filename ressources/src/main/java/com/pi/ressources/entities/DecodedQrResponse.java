package com.pi.ressources.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DecodedQrResponse {
    private String qrString;

    @Id
    @JsonIgnore
    private Long idDecQrResp;
    public DecodedQrResponse() {

    }

    public DecodedQrResponse(String qrString) {
        this.qrString = qrString;
    }



}
