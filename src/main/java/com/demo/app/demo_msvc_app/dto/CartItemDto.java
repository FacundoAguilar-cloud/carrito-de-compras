package com.demo.app.demo_msvc_app.dto;

import java.math.BigDecimal;



import lombok.Data;

@Data
public class CartItemDto {
private Long itemId;
private Integer quantity;
private BigDecimal pricePerUnit;
private ProductDto product;

}
