package com.demo.app.demo_msvc_app.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.Role;

public interface RoleRepository extends CrudRepository <Role, Long> {
Optional <Role> findRoleByName(String role);
}
