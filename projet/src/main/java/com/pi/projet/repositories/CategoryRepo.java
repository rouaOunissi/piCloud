package com.pi.projet.repositories;

import com.pi.projet.DTO.ResponseCategory;
import com.pi.projet.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

public Category findByName(String name);


}
