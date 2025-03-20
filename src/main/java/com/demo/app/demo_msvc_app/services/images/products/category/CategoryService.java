package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.demo.app.demo_msvc_app.entities.Category;
import com.demo.app.demo_msvc_app.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceIMPL {
    private final CategoryRepository categoryRepository;
    
    @Override
    public Optional <Category> getCategoryById(Long id) {
      return categoryRepository.findById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existByName(c.getName()))
        .map(categoryRepository:: save).orElseThrow(() -> new RuntimeException("This category already exists"));
    }
    @Override
    public Category updateCategory(Category category, Long id) {
        return getCategoryById(id).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }
    //esto lo que hace es buscarlo y si lo encuentra lo de borra, pero si no está presenete arroja error y avisa que la categoria no fue encontrada
    @Override
    public void deleteCategoryById(Long id) {
       categoryRepository.findById(id)
       .ifPresentOrElse(categoryRepository:: delete, () -> {
        throw new RuntimeException("Category not Found");
       });
    }

}
