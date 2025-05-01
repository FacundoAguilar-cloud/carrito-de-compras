package com.demo.app.demo_msvc_app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.demo_msvc_app.dto.OrderDto;
import com.demo.app.demo_msvc_app.entities.Order;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.order.OrderServiceIMPL;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequiredArgsConstructor 
@RequestMapping("/api")
public class OrderController {
private final OrderServiceIMPL orderService; 

@PostMapping("/create-order")
public ResponseEntity <ApiResponse> createOrder(@RequestParam Long userId) {
try {
    Order order = orderService.placeOrder(userId);
return ResponseEntity.ok(new ApiResponse("Order generated successfully", order));

} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occured, please try again", e.getMessage()));
}

}

@GetMapping("/get-order/{orderId}")
public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
    try {
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", order));
    } catch (ElementsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found, please try again", e.getMessage()));
    }
}

@GetMapping("/get-order-userId/{userId}")
public ResponseEntity <ApiResponse> getOrderByUserId(@PathVariable Long userId) {
    try {
        //aca deberiamos de trabajar obligatoriamente con un DTO por una cuestion de conveniencia
        //si retornamos directamente la clase vamos a devolver cosas que realmente no necesitamos(orderItem, User, etc)
        List <OrderDto> order = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Item Order retrieved successfully", order));
    } catch (ElementsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Order not found", userId));
    }
}




}
