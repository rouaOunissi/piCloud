package com.pi.projet.ServiceImp;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.Services.ProjetService;
import com.pi.projet.entities.Category;
import com.pi.projet.entities.ProjectStatus;
import com.pi.projet.entities.Projet;
import com.pi.projet.repositories.CategoryRepo;
import com.pi.projet.repositories.ProjetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetServiceImp implements ProjetService {


    private final ProjetRepo projetRepo ;
    private final CategoryRepo categoryRepo;


    @Override
    public ResponseProjet createProject(RequestProjet requestProjet) {
        Projet projet=this.mapDTOToModel(requestProjet);
        return this.mapModelToDTO(projetRepo.save(projet));

    }

    @Override
    public ResponseEntity<List<ResponseProjet>> findProjetByCategory_Id(Long id) {
        List<Projet> projets = projetRepo.findProjetByCategory_Id(id);
        List<ResponseProjet> responseProjets = projets.stream().map(this::mapModelToDTO).toList();

        return ResponseEntity.ok(responseProjets);
    }


    public Projet mapDTOToModel (RequestProjet requestProjet){
        Optional<Category> category = categoryRepo.findById(requestProjet.getCategoryId());
        return category.map(value -> Projet.builder()
                .title(requestProjet.getTitle())
                .description(requestProjet.getDescription())
                .creatorId(requestProjet.getCreatorId())
                .category(value)
                .status(ProjectStatus.OPEN)
                .build()).orElse(null);
    }

    public ResponseProjet mapModelToDTO(Projet projet){
        return ResponseProjet.builder()
                .title(projet.getTitle())
                .description(projet.getDescription())
                .creationDate(projet.getCreationDate())
                .status(projet.getStatus())
                .build();

    }



}
