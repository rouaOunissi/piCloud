package com.pi.ressources.Dao;

import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RessourceDao extends JpaRepository<Ressource,Long> {

    @Query("select r from Ressource r where r.TypeR=:typeRessource")
    List<Ressource> findByTypeRessource(TypeRessource typeRessource);

    @Query ("select r from Ressource r where r.idUser = :userid")
    List<Ressource> findByUserId(Long userid);

    @Query("SELECT r FROM Ressource r WHERE r.urlFile = :urlFile")
    Ressource findByUrlFile(@Param("urlFile") String urlFile);

}
