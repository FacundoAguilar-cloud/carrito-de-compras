package com.demo.app.demo_msvc_app.services.order;

import java.util.List;

import com.demo.app.demo_msvc_app.dto.OrderDto;
import com.demo.app.demo_msvc_app.entities.Order;

public interface OrderServiceIMPL {

 Order placeOrder(Long userId);

 OrderDto getOrder(Long orderId);

 List<OrderDto> getUserOrders(Long userId);

 OrderDto convertToDto(Order order);

 Order cancelOrder(Long orderId);
}
