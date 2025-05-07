package com.demo.app.demo_msvc_app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByName(String name);

    List<Product> findByBrand(String brand);

    Long countProductsByName(String name);

    boolean existsByNameAndBrand(String name, String brand);

}
