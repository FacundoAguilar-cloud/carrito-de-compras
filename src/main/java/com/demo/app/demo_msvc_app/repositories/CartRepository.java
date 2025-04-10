package com.demo.app.demo_msvc_app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

}
