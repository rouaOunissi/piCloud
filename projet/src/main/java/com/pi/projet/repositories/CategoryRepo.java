package com.pi.projet.repositories;

import com.pi.projet.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

public Category findByName(String name);
}
