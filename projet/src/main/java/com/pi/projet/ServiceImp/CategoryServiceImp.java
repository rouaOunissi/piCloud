package com.pi.projet.ServiceImp;

import com.pi.projet.Services.CategoryService;
import com.pi.projet.entities.Category;
import com.pi.projet.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepo categoryRepo ;

    @Override
    public ResponseEntity<?> createCategory(String categoryName) {
            Category checkCategory = categoryRepo.findByName(categoryName);

        if (checkCategory==null){
        Category category = new Category();
        category.setName(categoryName);
        categoryRepo.save(category);
        return ResponseEntity.ok("Category added Successfully");
        }
        return ResponseEntity.badRequest().body("Category already Exists");

    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<String> caStrings = categories.stream().map(Category::getName).toList();
        return caStrings;
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id,String catName) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            category.get().setName(catName);
            categoryRepo.save(category.get());
            return ResponseEntity.ok("Category updated successfully");
        } else
            return ResponseEntity.badRequest().body("Category does not exist");
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
       Optional <Category> category = categoryRepo.findById(id);
       if(category.isPresent()){
        categoryRepo.deleteById(id);
        return ResponseEntity.ok("Category deleted successfully");
       }
       else
           return ResponseEntity.badRequest().body("Category Do Not Exist");
    }


}
