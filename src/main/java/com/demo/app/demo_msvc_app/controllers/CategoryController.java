package com.demo.app.demo_msvc_app.controllers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.app.demo_msvc_app.entities.Category;
import com.demo.app.demo_msvc_app.exceptions.AlreadyExistExcp;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.images.products.category.CategoryServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
private final CategoryServiceIMPL categoryService;

@GetMapping("/get-all")
public  ResponseEntity<ApiResponse> getAllCategories(){
    try {
        List <Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("All categories getted succesfully", null));
        
        
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}

@GetMapping("/get-by-id{id}")
public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
    try { 
        Optional<Category> category = categoryService.getCategoryById(id);
        
            return ResponseEntity.ok(new ApiResponse("Category retrieved succesfully", category.get()));
        
       
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found, please try again", Map.of("requestedId", id)));
    }
    
    
}

@GetMapping("/get-by-name")
public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name) {
    try {
        Category theName = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("Category name founded succesfully", theName));
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
   
}
   


@PostMapping("/add")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity <ApiResponse> addCategory (@RequestBody Category name) {
       try {
          Category category = categoryService.createCategory(name);
          return ResponseEntity.ok(new ApiResponse("Category added succesfully!", null));
        }
        
        catch (AlreadyExistExcp e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Category already exist", null));
       }
       
        
}
@DeleteMapping("/delete")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity <ApiResponse> deleteCategoryById(@PathVariable Long id){
    try {
        categoryService.deleteCategoryById(id);
        
        return ResponseEntity.ok(new ApiResponse("Cagetory deleted succesfully", null));
        
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null)); 
    }

}

@PutMapping("update/{id}")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
   try {
    Category updatedCategory = categoryService.updateCategory(category, id);
    return ResponseEntity.ok(new ApiResponse("Category updated succesfulyy", null));
   } catch (NoSuchElementException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
   }
    
    
}


}
