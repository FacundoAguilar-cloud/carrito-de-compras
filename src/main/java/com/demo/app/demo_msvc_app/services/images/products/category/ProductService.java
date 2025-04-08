package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.demo.app.demo_msvc_app.entities.Category;
import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.repositories.CategoryRepository;
import com.demo.app.demo_msvc_app.repositories.ProductRepository;
import com.demo.app.demo_msvc_app.request.AddProductR;
import com.demo.app.demo_msvc_app.request.UpdateProductR;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceIMPL {

    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductR request) {

      Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
      .orElseGet(()-> {
        Category newCategory = new Category(request.getCategory().getName());
        return categoryRepository.save(newCategory);
      });

      request.setCategory(category);
      
      return productRepository.save(createProduct(request, category));
        
        
    }

    private Product createProduct(AddProductR request, Category category){
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            request.getCategory()
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return (List <Product>) productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
       return productRepository.findById(id).orElseGet(null);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)

        .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Category category = product.getCategory();
        if (category != null) {
            category.getProducts().remove(product);
            product.setCategory(null);
            categoryRepository.save(category);
            
        }
        productRepository.delete(product);
        }

    @Override
    public Product updateProduct(UpdateProductR request, Long productId) {
       return productRepository.findById(productId)
       .map(existingProduct -> updateExistingProduct(existingProduct, request))
       .map(productRepository :: save)           
       .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
    
    private Product updateExistingProduct(Product existingProduct, UpdateProductR request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    };

    @Override
    public List<Product> getProductsByCategory(String category) {
        return (List <Product>) productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return (List <Product>) productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return (List <Product>) productRepository.findByBrand(brand);
    }

    @Override
    public Long countProductsByName(String name) {
       return productRepository.countProductsByName(name);
    }

}
