package com.demo.app.demo_msvc_app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.User;

public interface UserRepository extends CrudRepository <User, Long> {
Boolean existByEmail(String email);
}
