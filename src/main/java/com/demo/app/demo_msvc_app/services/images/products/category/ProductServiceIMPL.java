package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;


import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.request.AddProductR;
import com.demo.app.demo_msvc_app.request.UpdateProductR;

public interface ProductServiceIMPL {
List <Product> getAllProducts();
Product getProductById(Long id);
Product addProduct(AddProductR product);
void deleteProduct(Long id);
Product updateProduct(UpdateProductR product, Long productId);
List <Product> getProductsByCategory(String category);
List <Product> getProductsByName(String name);
List <Product> getProductsByBrand(String brand);
Long countProductsByName(String name);

}
