package com.pi.projet.repositories;

import com.pi.projet.DTO.CategoryStats;
import com.pi.projet.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

public Category findByName(String name);

    @Query("SELECT new com.pi.projet.DTO.CategoryStats(c.name, COUNT(p)) " +
            "FROM Category c JOIN c.projects p " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(p) DESC")
    List<CategoryStats> countProjectsByCategory();
}




