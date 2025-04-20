package com.demo.app.demo_msvc_app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.Order;

public interface OrderRepository extends CrudRepository <Order, Long> {

}
