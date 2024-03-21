package com.pi.projet.Services;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.entities.Projet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjetService {

    public ResponseProjet createProject(RequestProjet project , String  bearerToken );

    public ResponseEntity<List<ResponseProjet>> findProjetByCategory_Id(Long id);
}
