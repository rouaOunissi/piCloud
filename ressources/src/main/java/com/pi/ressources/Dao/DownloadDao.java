package com.pi.ressources.Dao;

import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.entities.Download;
import com.pi.ressources.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DownloadDao extends JpaRepository<Download,Long> {


    @Query("select r from Download r where r.idUser = :userid")
    List<Download> findByUserId(Long userid);

    @Query("SELECT COUNT(d) FROM Download d WHERE DATE(d.dateDownload) = CURRENT_DATE")
    Long countDownloadsByDay();

    @Query("SELECT COUNT(d) FROM Download d WHERE YEARWEEK(d.dateDownload) = YEARWEEK(CURRENT_DATE)")
    Long countDownloadsByWeek();


    @Query("SELECT COUNT(d) FROM Download d WHERE d.ressource.TypeR = :type")
    Long countDownloadsByType(TypeRessource type);

    @Query("SELECT d FROM Download d WHERE YEAR(d.dateDownload) = :year")
    List<Download> findAllByYear(int year);

    @Query(value = "SELECT d.id_ressource FROM download d GROUP BY d.id_ressource ORDER BY COUNT(d.id_ressource) DESC LIMIT 1", nativeQuery = true)
    Long findMostDownloadedResourceId();


}
