package com.demo.app.demo_msvc_app.repositories;


import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.Category;

public interface CategoryRepository extends CrudRepository <Category, Long> {
Category findByName(String name);

boolean existsByName(String name);
}
