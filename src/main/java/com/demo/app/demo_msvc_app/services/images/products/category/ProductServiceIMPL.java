package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;
import java.util.Optional;

import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.request.AddProductR;

public interface ProductServiceIMPL {
Product addProduct(AddProductR product);
List <Product> getAllProducts();
Optional <Product> getProductById(Long id);
void deleteProduct(Long id);
Product updateProduct(Product product, Long productId);
List <Product> getProductsByCategory(String category);
List <Product> getProductsByName(String name);
List <Product> getProductsByBrand(String brand);
Long countProductsByName(String name);

}
