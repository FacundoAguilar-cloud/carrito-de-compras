package com.demo.app.demo_msvc_app.controllers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.demo_msvc_app.dto.ProductDto;
import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.exceptions.AlreadyExistExcp;
import com.demo.app.demo_msvc_app.request.AddProductR;
import com.demo.app.demo_msvc_app.request.UpdateProductR;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.images.products.category.ProductServiceIMPL;

import lombok.RequiredArgsConstructor;
import lombok.var;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
private final ProductServiceIMPL productServiceIMPL;
@GetMapping("/get-all-products")
public  ResponseEntity<ApiResponse> getAllProducts(){
    try {
        //dejamos de trabajar con la entidad para usar el DTO
        List <Product> products = productServiceIMPL.getAllProducts();
        List <ProductDto> convertedProducts = productServiceIMPL.productsConverted(products);
        return ResponseEntity.ok(new ApiResponse("All products retrieved successfully", convertedProducts));
        
        
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
@GetMapping("/get-by-Category")
public  ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String category){
    try {
        List <Product> products = productServiceIMPL.getProductsByCategory(category);
        List <ProductDto> convertedProducts = productServiceIMPL.productsConverted(products);
        return ResponseEntity.ok(new ApiResponse("All products by category retrieved succesfully", convertedProducts));
        
        
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
@GetMapping("/get-product-by-name")
public  ResponseEntity<ApiResponse> getProductsByName(@RequestParam String name){
    try {
        List <Product> products = productServiceIMPL.getProductsByName(name);
        List <ProductDto> convertedProducts = productServiceIMPL.productsConverted(products);
        return ResponseEntity.ok(new ApiResponse("All products by name retrieved succesfully", convertedProducts));
        
        
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
@GetMapping("/get-by-brand")
public  ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand){
    try {
        List <Product> products = productServiceIMPL.getProductsByBrand(brand);
        List <ProductDto> convertedProducts = productServiceIMPL.productsConverted(products);
        return ResponseEntity.ok(new ApiResponse("All products by brand retrieved succesfully", convertedProducts));
        
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}


@GetMapping("/get-product-by-id/{productId}")
public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
    try { 
        Product product = productServiceIMPL.getProductById(productId);
        ProductDto productDto = productServiceIMPL.convertToDto(product);
        return ResponseEntity.ok(new ApiResponse("Product retrieved succesfully", productDto));
       }
        catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found, please try again", Map.of("requestedId", productId)));
    }
   
}

 //solo puede hacerlo un admin

@PostMapping("/add-product")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity <ApiResponse> addProduct (@RequestBody AddProductR name) {
       try {
          Product product = productServiceIMPL.addProduct(name);
          ProductDto productDto = productServiceIMPL.convertToDto(product);
          return ResponseEntity.ok(new ApiResponse("Product added succesfully!", productDto));
        }
        
        catch (AlreadyExistExcp e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
       }
       

    }
 //solo puede hacerlo un admin
  @DeleteMapping("/delete-product/{productId}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId) {
    try {
        productServiceIMPL.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
    } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse("Cannot delete product: it is linked to other data", null));
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Product not found", null));
    }
}

 //solo puede hacerlo un admin
    @PutMapping("update-product/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductR product) {
   try {
    Product updatedProduct = productServiceIMPL.updateProduct(product, productId);
    ProductDto productDto = productServiceIMPL.convertToDto(updatedProduct);
    return ResponseEntity.ok(new ApiResponse("Product updated succesfulyy", productDto));
   } catch (NoSuchElementException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
   }
    
    
}
@GetMapping("/count-products-by-name")
public ResponseEntity<ApiResponse> countProductsByName(@RequestParam String name) {
    try {
        var product = productServiceIMPL.countProductsByName(name);
        return ResponseEntity.ok(new ApiResponse("Product counted succesfulyy", product));
       } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
       }
}

    

}
