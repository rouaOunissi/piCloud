package com.pi.projet.ServiceImp;

import com.pi.projet.DTO.CategoryStats;
import com.pi.projet.DTO.MessageResponse;
import com.pi.projet.DTO.ResponseCategory;
import com.pi.projet.Services.CategoryService;
import com.pi.projet.entities.Category;
import com.pi.projet.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
            return ResponseEntity.ok(HttpStatus.CREATED)   ;     }

        return ResponseEntity.badRequest().body("Category already Exists");

    }

    @Override
    public List<?> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<ResponseCategory> caStrings = categories.stream().map(this::mapModelToDTO).toList();
        return caStrings;
    }

    @Override
    public ResponseEntity<?> getAllCategoriesAdmin() {

        List<Category> categories = categoryRepo.findAll();
        List<ResponseCategory> responseCategories = categories.stream().map(this::mapModelToDTO).toList();
        return  ResponseEntity.ok(responseCategories);
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, String catName) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            category.get().setName(catName);
            categoryRepo.save(category.get());
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } else
            return ResponseEntity.badRequest().body("Category does not exist");
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
       Optional <Category> category = categoryRepo.findById(id);
       if(category.isPresent()){
        categoryRepo.deleteById(id);
           return ResponseEntity.ok(HttpStatus.ACCEPTED);
       }
       else
           return ResponseEntity.badRequest().body("Category Do Not Exist");
    }

    @Override
    public List<CategoryStats> getProjectCategoryStatistics() {
        return categoryRepo.countProjectsByCategory();
    }

    public ResponseCategory mapModelToDTO(Category category){
        return ResponseCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .nbr_projects(category.getProjects().size())
                .build();
    }

}
