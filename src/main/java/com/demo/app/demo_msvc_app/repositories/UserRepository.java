package com.demo.app.demo_msvc_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.app.demo_msvc_app.entities.User;

public interface UserRepository extends CrudRepository <User, Long> {
Boolean existsByEmail(String email);


@Query("SELECT u FROM User u LEFT JOIN FETCH u.cart WHERE u.id = :userId")
Optional<User> findByIdWithCart(@Param("userId") Long userId);


User findByEmail(String email);

}
