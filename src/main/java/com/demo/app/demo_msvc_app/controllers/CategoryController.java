package com.demo.app.demo_msvc_app.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.demo_msvc_app.entities.Category;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.images.products.category.CategoryServiceIMPL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {
private final CategoryServiceIMPL categoryService;

public  ResponseEntity<ApiResponse> getAllCategories(){
    try {
        List <Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("All categories getted succesfully", null));
        
        
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}

}
