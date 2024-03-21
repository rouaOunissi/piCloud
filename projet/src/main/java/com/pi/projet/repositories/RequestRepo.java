package com.pi.projet.repositories;

import com.pi.projet.DTO.ResponseRequest2;
import com.pi.projet.entities.Projet;
import com.pi.projet.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepo extends JpaRepository<Request,Long> {

    public List<Request> getRequestsByProject(Projet projet);

    @Query("SELECT new com.pi.projet.DTO.ResponseRequest2(p.title, r.status) FROM Request r JOIN r.project p WHERE r.encadreurId = :encadreurId")
    public List<ResponseRequest2> getRequestsByEncadreurId(@Param("encadreurId")Long id);
}
