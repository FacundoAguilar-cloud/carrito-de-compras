package com.demo.app.demo_msvc_app.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.demo.app.demo_msvc_app.dto.OrderItemDTO;
import com.demo.app.demo_msvc_app.entities.Order;
import com.demo.app.demo_msvc_app.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponseDTO {
private Long orderId; 
private LocalDate orderDate;
private BigDecimal totalOrderAmount;
private OrderStatus orderStatus;
private List <OrderItemDTO> orderItems;
private String message;
private Object data;
   
 public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        this.orderDate = order.getOrderDate();
        this.totalOrderAmount = order.getTotalOrderAmount();
        this.orderStatus = order.getOrderStatus();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }











}
