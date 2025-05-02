package com.demo.app.demo_msvc_app.dto;

import java.math.BigDecimal;

import com.demo.app.demo_msvc_app.entities.OrderItem;

import lombok.Data;
@Data
public class OrderItemDTO {
private Long productId;
private String productName;
private String productBrand;
private int quantity;
private BigDecimal pricePerUnit;


  public OrderItemDTO(OrderItem orderItem) {
        this.productId = orderItem.getId();
        this.productId = orderItem.getProduct().getId(); 
        this.productName = orderItem.getProduct().getName();
        this.quantity = orderItem.getQuantity();
        this.pricePerUnit = orderItem.getPrice();
    }



}


