package com.pi.projet.repositories;

import com.pi.projet.entities.ProjectStatus;
import com.pi.projet.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepo extends JpaRepository<Projet,Long> {


    public List<Projet> findProjetByCategory_Id(Long id);

    @Query(value = "SELECT * FROM projet as p where p.status='OPEN'",nativeQuery = true)
    public List<Projet> findOpenProjet();

    @Query(value = "SELECT * FROM projet as p where p.status='CLOSED'",nativeQuery = true)
    public List<Projet> findClosedProjet();

    public List<Projet> findProjetByCreatorId(Long id);
}
