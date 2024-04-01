package com.pi.projet.Controllers;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseCategory;
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
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService ;

    @PostMapping
    public ResponseEntity<?> createCategory( @RequestParam String categoryName) {
     return categoryService.createCategory(categoryName);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
    List<?> categories = categoryService.getAllCategories();
    return ResponseEntity.ok(categories);
    }

    @GetMapping("admin-cat")
    public ResponseEntity<?> getAllCategoriesAdmin(){

        return categoryService.getAllCategoriesAdmin();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,@RequestParam String categoryName){
        return categoryService.updateCategory(id,categoryName);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }
}
