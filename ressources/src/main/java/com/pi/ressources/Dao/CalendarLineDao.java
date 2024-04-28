package com.pi.ressources.Dao;

import com.pi.ressources.entities.CalendarLine;

import com.pi.ressources.entities.Download;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarLineDao extends JpaRepository<CalendarLine,Long> {


    @Query("select c from CalendarLine c where c.idUser = :userid")
    List<CalendarLine> findByUserId(Long userid);


}
