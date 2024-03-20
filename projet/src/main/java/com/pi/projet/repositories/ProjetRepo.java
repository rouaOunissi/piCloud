package com.pi.projet.repositories;

import com.pi.projet.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepo extends JpaRepository<Projet,Long> {

    @Query
    public List<Projet> findProjetByCategory_Id(Long id);
}
