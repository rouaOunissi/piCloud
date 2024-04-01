package com.pi.projet.Services;

import com.pi.projet.DTO.RequestRequest;
import com.pi.projet.DTO.ResponseProjet;
import org.springframework.http.ResponseEntity;

public interface RequestService {
    public ResponseEntity<?> createRequest(RequestRequest requestRequest);

    public ResponseEntity<?> getRequestByProjetId(Long id);

    public ResponseEntity<?> getRequestByUserId(Long id);


    public ResponseEntity<?> acceptRequest(Long id);

    public ResponseEntity<?> declineRequest(Long id);

    public ResponseEntity<?> deleteRequest (Long id);
}
