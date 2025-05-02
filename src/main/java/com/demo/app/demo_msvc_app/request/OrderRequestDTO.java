package com.demo.app.demo_msvc_app.request;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDTO {

private Long userId;
private List <OrderItemRequestDTO> items;

@Data
public static class OrderItemRequestDTO{
    private Long productId;
    
    private Integer quantity;
}

}
