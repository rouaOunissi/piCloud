package com.pi.ressources.Dao;

import com.pi.ressources.entities.Download;
import com.pi.ressources.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DownloadDao extends JpaRepository<Download,Long> {


    @Query("select r from Download r where r.idUser = :userid")
    List<Download> findByUserId(Long userid);



}
