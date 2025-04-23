package com.demo.app.demo_msvc_app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;



import lombok.Data;
@Data
public class OrderDto {
private Long id;
private Long userId;
private LocalDateTime orderDate;
private BigDecimal totalOrderAmount;
private String orderStatus;
private List <OrderItemDTO> items;
}
