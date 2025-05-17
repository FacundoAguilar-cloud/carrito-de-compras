package com.demo.app.demo_msvc_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.entities.User;



public interface CartRepository extends CrudRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Optional<Cart> findByUser_Id(@Param("userId") Long userId);

    boolean existsByUser_Id(Long userId);


    Optional <Cart> findByUser(User user);
}
