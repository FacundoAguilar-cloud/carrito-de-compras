package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;
import java.util.Optional;

import com.demo.app.demo_msvc_app.entities.Category;

public interface CategoryServiceIMPL {
Optional <Category> getCategoryById(Long id);

Category getCategoryByName(String name);

List <Category> getAllCategories();

Category createCategory(Category category);

Category updateCategory(Category category, Long id); 

void deleteCategoryById(Long id); 

}
