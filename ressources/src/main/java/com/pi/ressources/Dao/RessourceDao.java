package com.pi.ressources.Dao;

import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RessourceDao extends JpaRepository<Ressource, Long> {

    @Query("select r from Ressource r where r.TypeR=:typeRessource")
    List<Ressource> findByTypeRessource(TypeRessource typeRessource);

    @Query("select r from Ressource r where r.idUser = :userid")
    List<Ressource> findByUserId(Long userid);

    @Query("SELECT r FROM Ressource r WHERE r.urlFile = :urlFile")
    Ressource findByUrlFile(@Param("urlFile") String urlFile);

    @Query("SELECT r FROM Ressource r WHERE LOWER(r.titre) LIKE %:titre%")
    List<Ressource> findByTitreContainingIgnoreCase(@Param("titre") String titre);

    @Query("SELECT COUNT(r) FROM Ressource r WHERE r.idRessource = :ressourceId AND r.idUser = :userId")
    int countReactionsByRessourceIdAndUserId(Long ressourceId, Long userId);

    @Query("SELECT r FROM Ressource r ORDER BY r.nbrReact DESC")
    List<Ressource> findAllOrderByNbrReactDesc();

    @Query("SELECT r FROM Ressource r WHERE LOWER(r.titre) LIKE %:synonym% OR LOWER(r.description) LIKE %:synonym%")
    List<Ressource> findBySynonym(String synonym);

    @Query("SELECT r FROM Ressource r WHERE lower(r.titre) LIKE lower(concat('%', :keyword, '%')) OR lower(r.description) LIKE lower(concat('%', :keyword, '%'))")
    List<Ressource> findByTitleContainingOrDescriptionContaining(String keyword);


}
