package com.pi.projet.Controllers;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.Services.CategoryService;
import com.pi.projet.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projets")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService ;

    @PostMapping("/category")
    public ResponseEntity<String> createProject(@RequestBody String categoryName) {
     return categoryService.createCategory(categoryName);
    }
}
