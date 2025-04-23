package com.demo.app.demo_msvc_app.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class OrderItemDTO {
private Long productId;
private String productName;
private int quantity;
private BigDecimal price;
}
