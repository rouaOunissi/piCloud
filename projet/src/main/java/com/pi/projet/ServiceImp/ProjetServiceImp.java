package com.pi.projet.ServiceImp;

import com.pi.projet.DTO.MessageResponse;
import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.RequestProjet2;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.Services.ProjetService;
import com.pi.projet.entities.Category;
import com.pi.projet.entities.ProjectStatus;
import com.pi.projet.entities.Projet;
import com.pi.projet.repositories.CategoryRepo;
import com.pi.projet.repositories.ProjetRepo;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjetServiceImp implements ProjetService {


    private final ProjetRepo projetRepo ;
    private final CategoryRepo categoryRepo;



    @Override
    public ResponseEntity<?> createProject(RequestProjet requestProjet) {

        if(requestProjet.getTitle()!=null && requestProjet.getDescription()!=null && requestProjet.getCategoryId()!=null && requestProjet.getCreatorId()!=null){

            Projet projet = this.mapDTOToModel(requestProjet);
       //     projet.setStatus(ProjectStatus.PENDING);
            projetRepo.save(projet);
            ResponseProjet responseProjet = this.mapModelToDTO(projet);
            return ResponseEntity.ok(responseProjet);

        }else {
            return ResponseEntity.badRequest().body("bad request");
        }



    }

    @Override
    public ResponseEntity<List<ResponseProjet>> findProjetByCategory_Id(Long id) {
        List<Projet> projets = projetRepo.findProjetByCategory_Id(id);
        List<ResponseProjet> responseProjets = projets.stream().map(this::mapModelToDTO).toList();

        return ResponseEntity.ok(responseProjets);
    }

    @Override
    public List<ResponseProjet> getAllAdminAcceptedProjects() {
        List<Projet> projets = projetRepo.getAllAdminAccepted();
        List<ResponseProjet> responseProjets = projets.stream().map(this::mapModelToDTO).toList();
        return responseProjets;
    }

    @Override
    public List<ResponseProjet> getAllAdminPendingProjects() {
        List<Projet> projets = projetRepo.getAllAdminPending();
        List<ResponseProjet> responseProjets = projets.stream().map(this::mapModelToDTO).toList();
        return responseProjets;
    }

    @Override
    public List<ResponseProjet> getAllAdminDeclinedProjects() {
        List<Projet> projets = projetRepo.getAllAdminDeclined();
        List<ResponseProjet> responseProjets = projets.stream().map(this::mapModelToDTO).toList();
        return responseProjets;
    }

    @Override
    public ResponseEntity<?> adminAcceptProject(Long id) {
        Optional<Projet> projet1=projetRepo.findById(id);
        if(projet1.isPresent()){
            Projet projet11 = projet1.get();
            projet11.setStatus(ProjectStatus.OPEN);
            return ResponseEntity.ok(projetRepo.save(projet11));

        }
        else
            return ResponseEntity.badRequest().body("Error accepting project");
    }

    @Override
    public ResponseEntity<?> adminDeclineProject(Long id) {
        Optional<Projet> projet1=projetRepo.findById(id);
        if(projet1.isPresent()){
            Projet projet11 = projet1.get();
            projet11.setStatus(ProjectStatus.DECLINED);
            return ResponseEntity.ok(projetRepo.save(projet11));

        }
        else
            return ResponseEntity.badRequest().body("Error declining project");
    }

    @Override
    public ResponseEntity<?> updateProject(Long id, RequestProjet2 requestProjet2) {
        Optional<Projet> projet= projetRepo.findById(id);
        if(projet.isPresent()){
            Projet projet1 = projet.get();
            projet1.setTitle(requestProjet2.getTitle());
            projet1.setDescription(requestProjet2.getDescription());
            return ResponseEntity.ok(projetRepo.save(projet1));
        }
        else
            return ResponseEntity.badRequest().body("Error declining project");
    }


    @Override
    public ResponseEntity<?> updateProjetTitle(Long id, String title) {
        Optional<Projet> projet = projetRepo.findById(id);
        if(projet.isPresent() && title!=null){
            Projet projet1 = projet.get();
            projet1.setTitle(title);
            return ResponseEntity.ok(this .mapModelToDTO(projetRepo.save(projet1)));
        }
        else
            return ResponseEntity.badRequest().body("project do not exists");
    }

    @Override
    public ResponseEntity<?> updateProjetDescription(Long id, String desc) {
        Optional<Projet> projet = projetRepo.findById(id);
        if(projet.isPresent() && desc!=null){
            Projet projet1 = projet.get();
            projet1.setDescription(desc);
            return ResponseEntity.ok(this .mapModelToDTO(projetRepo.save(projet1)));
        }
        else
            return ResponseEntity.badRequest().body("project do not exists");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProjetCategory(Long id, Long catId) {
        Optional<Projet> projet = projetRepo.findById(id);
        Optional<Category> category = categoryRepo.findById(catId);
        if(projet.isPresent() && category.isPresent()){
            Projet projet1 = projet.get();
            Category category1 = category.get();
            projet1.setCategory(category1);
            projetRepo.save(projet1);
            return ResponseEntity.ok(this.mapModelToDTO(projet1));
        }
        else return ResponseEntity.badRequest().body("Error While Updating Category !");
    }

    @Override
    public ResponseEntity<?> deleteProjet(Long id) {
        Optional<Projet> projet = projetRepo.findById(id);
        if(projet.isPresent()){
            projetRepo.delete(projet.get());
           return ResponseEntity.ok().body(new MessageResponse("Project deleted successfully!"));
        }
        else
            return ResponseEntity.badRequest().body("Project Do Not Exist !");
    }

    @Override
    public ResponseEntity<?> getAllOpenProjet() {
     List<Projet> projets=projetRepo.findOpenProjet();
     List<ResponseProjet> responseProjets = projets.stream().map(this::mapModelToDTO).toList();
        return ResponseEntity.ok(responseProjets);
    }

    @Override
    public ResponseEntity<?> getAllClosedProjet() {
        List<Projet> projets=projetRepo.findClosedProjet();
        List<ResponseProjet> responseProjets = projets.stream().map(this::mapModelToDTO).toList();
        return ResponseEntity.ok(responseProjets);
    }

    @Override
    public ResponseEntity<?> openProjet(Long id) {
        Optional<Projet> projet = projetRepo.findById(id);
        if(projet.isPresent()){
            Projet projet1 = projet.get();
            projet1.setStatus(ProjectStatus.OPEN);
            projetRepo.save(projet1);
            return ResponseEntity.ok("Project Opened");
        }
        else
            return ResponseEntity.badRequest().body("Project Do Not Exist");
    }

    @Override
    public ResponseEntity<?> closeProjet(Long id) {
        Optional<Projet> projet = projetRepo.findById(id);
        if(projet.isPresent()){
            Projet projet1 = projet.get();
            projet1.setStatus(ProjectStatus.CLOSED);
            projetRepo.save(projet1);
            return ResponseEntity.ok("Project  Closed");
        }
        else
            return ResponseEntity.badRequest().body("Project Do Not Exist");
    }

    @Override
    public ResponseEntity<?> getUserProjets(Long id) {
        List<Projet> projets = projetRepo.findProjetByCreatorId(id);
        if(projets.isEmpty())
            return ResponseEntity.ok(new ArrayList<>());
        else
            return ResponseEntity.ok(projets.stream().map(this::mapModelToDTO).toList());
    }



    public Projet mapDTOToModel (RequestProjet requestProjet){
        Optional<Category> category = categoryRepo.findById(requestProjet.getCategoryId());
        return category.map(value -> Projet.builder()
                .title(requestProjet.getTitle())
                .description(requestProjet.getDescription())
                .category(value)
                .creatorId(requestProjet.getCreatorId())
                .status(ProjectStatus.PENDING)
                .build()).orElse(null);
    }

    public ResponseProjet mapModelToDTO(Projet projet) {
        return ResponseProjet.builder()
                .id(projet.getId())
                .title(projet.getTitle())
                .description(projet.getDescription())
                .creationDate(projet.getCreationDate())
                .status(projet.getStatus())
                .build();
    }



}
