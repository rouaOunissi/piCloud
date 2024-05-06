package com.pi.ressources.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@Entity
public class DecodedQrRequest {
    @Id
    private Long idDecodedQrRequest;
    @Lob
    private byte[] qrCodeContent;

}
