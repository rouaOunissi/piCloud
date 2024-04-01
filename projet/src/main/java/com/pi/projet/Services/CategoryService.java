package com.pi.projet.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoryService {
    public ResponseEntity<?> createCategory(String categoryName);


    public List<?> getAllCategories();

    public ResponseEntity<?> getAllCategoriesAdmin();

    public ResponseEntity<?> updateCategory(Long id,String catName);

    public ResponseEntity<?> deleteCategory(Long id );




}