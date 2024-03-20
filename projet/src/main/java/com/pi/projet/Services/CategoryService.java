package com.pi.projet.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface CategoryService {
    public ResponseEntity<String> createCategory(String categoryName);


}