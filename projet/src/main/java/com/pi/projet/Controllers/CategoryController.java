package com.pi.projet.Controllers;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.Services.CategoryService;
import com.pi.projet.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projets/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService ;

    @PostMapping
    public ResponseEntity<?> createCategory( String categoryName) {
     return categoryService.createCategory(categoryName);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllCategories(){
    List<String> categories = categoryService.getAllCategories();
    return ResponseEntity.ok(categories);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,String catName){
        return ResponseEntity.ok(categoryService.updateCategory(id,catName));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
