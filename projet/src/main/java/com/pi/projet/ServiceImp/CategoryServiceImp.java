package com.pi.projet.ServiceImp;

import com.pi.projet.FeignClients.UserProfile;
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

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<String> caStrings = categories.stream().map(Category::getName).toList();
        return caStrings;
    }

    @Override
    public String updateCategory(Long id,String catName) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            category.get().setName(catName);
            categoryRepo.save(category.get());
            return "Category updated successfully";
        } else
            return "Category does not exist";
    }



}
