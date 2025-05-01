package com.demo.app.demo_msvc_app.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.demo.app.demo_msvc_app.dto.OrderItemDTO;
import com.demo.app.demo_msvc_app.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderResponseDTO {
private Long orderId; 
private LocalDate orderDate;
private BigDecimal totalOrderAmount;
private OrderStatus orderStatus;
private List <OrderItemDTO> orderItems;
   
}
