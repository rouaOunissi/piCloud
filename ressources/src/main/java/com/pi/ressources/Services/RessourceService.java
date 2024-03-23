package com.pi.ressources.Services;

import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.entities.Ressource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RessourceService {


    Ressource addRessource(Ressource ressource);

    Optional<Ressource> addRessourceFile(Ressource ressource, MultipartFile file);

    List<Ressource> findAll();

    Ressource findById(Long id);


    List<Ressource> getByIdUser(Long userid);


    Ressource getByUrlFile(String urlFile);

    List<Ressource> getRessourcesByType(TypeRessource typeResoource);

    Ressource updateRessource(Long idRessource, Ressource ressource);

    String deteleRessource(Long id, Long idUser);

    //String deteleRessource(Long id);
}
