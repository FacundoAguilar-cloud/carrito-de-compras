package com.demo.app.demo_msvc_app.services.order;

import org.springframework.stereotype.Service;

import com.demo.app.demo_msvc_app.entities.Order;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceIMPL {
    private final OrderRepository orderRepository;
    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ElementsNotFoundException("Order not found"));
    }

}
