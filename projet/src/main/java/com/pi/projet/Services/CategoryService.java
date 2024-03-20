package com.pi.projet.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoryService {
    public ResponseEntity<String> createCategory(String categoryName);


    public List<String> getAllCategories();

    public String updateCategory(Long id,String catName);


}