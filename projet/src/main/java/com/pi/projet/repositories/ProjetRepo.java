package com.pi.projet.repositories;

import com.pi.projet.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetRepo extends JpaRepository<Projet,Long> {
}
