package com.pi.ressources.Services;

import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.entities.Ressource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RessourceService {


    Ressource addRessource(Ressource ressource);

    Optional<Ressource> addRessourceFile(Ressource ressource, MultipartFile file);

    List<Ressource> findAll();

    Ressource findById(Long id);


    List<String> getAllTypeRessourceNames();

    List<Ressource> getByIdUser(Long userid);


    Ressource getByUrlFile(String urlFile);

    List<Ressource> getRessourcesByType(TypeRessource typeResoource);


    Optional<Ressource> updateRessource(Long idRessource, Ressource ressource, MultipartFile file);

    ResponseEntity<?> deteleRessource(Long id);

    String GenerateInvoicePDF(Long id);

    List<Ressource> findByTitreContaining(String titre);


    boolean hasUserReactedToResource(long resourceId, long userId);

    ResponseEntity<?> reactToRessource(Long idRess);

    List<Ressource> getRessourcesOrderedByNbrReactDesc();


    List<Ressource> searchRessourcesBySynonyms(String word);

    List<Ressource> searchRessourcesByKeyword(String keyword);
}
