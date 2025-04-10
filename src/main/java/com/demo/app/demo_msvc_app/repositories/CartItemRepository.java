package com.demo.app.demo_msvc_app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.CartItem;

public interface CartItemRepository extends CrudRepository <CartItem, Long> {

    void deleteAllByCartId(Long id);

}
