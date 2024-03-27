package com.pi.projet.Services;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.entities.Projet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjetService {

    public ResponseEntity<?> createProject(RequestProjet project);

    public ResponseEntity<List<ResponseProjet>> findProjetByCategory_Id(Long id);

    public List<ResponseProjet> getAllAdminAcceptedProjects();
    public List<ResponseProjet> getAllAdminPendingProjects();

    public List<ResponseProjet> getAllAdminDeclinedProjects();

    public ResponseEntity<?> adminAcceptProject(Long id);
    public ResponseEntity<?> adminDeclineProject(Long id);


    public ResponseEntity<?> updateProjetTitle(Long id, String title ) ;

    public ResponseEntity<?> updateProjetDescription(Long id, String desc ) ;

    public ResponseEntity<?> updateProjetCategory(Long id, Long catId ) ;

    public ResponseEntity<?> deleteProjet(Long id);

    public ResponseEntity<?> getAllOpenProjet();

    public ResponseEntity<?> getAllClosedProjet();

    public ResponseEntity<?> openProjet(Long id);

    public ResponseEntity<?> closeProjet(Long id);

    public ResponseEntity<?> getUserProjets(Long id) ;




}
