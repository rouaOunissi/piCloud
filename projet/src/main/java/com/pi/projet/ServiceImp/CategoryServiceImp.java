package com.pi.projet.ServiceImp;

import com.pi.projet.Services.CategoryService;
import com.pi.projet.entities.Category;
import com.pi.projet.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepo categoryRepo ;

    @Override
    public ResponseEntity<String> createCategory(String categoryName) {
            Category checkCategory = categoryRepo.findByName(categoryName);

        if (checkCategory==null){
        Category category = new Category();
        category.setName(categoryName);
        categoryRepo.save(category);
        return ResponseEntity.ok("Category added Successfully");
        }
        return ResponseEntity.ok("Category already Exists");

    }
}
