package com.demo.app.demo_msvc_app.services.order;

import java.util.List;

import com.demo.app.demo_msvc_app.dto.OrderDto;
import com.demo.app.demo_msvc_app.entities.Order;

public interface OrderServiceIMPL {
//Con esto los usuarios van a poder generar la orden, todavia esa parte del proyecto no fue implementada pero necesitamos el método así ya lo dejamos creado.
 Order placeOrder(Long userId);

 OrderDto getOrder(Long orderId);

 List<OrderDto> getUserOrders(Long userId);

 OrderDto convertToDto(Order order);

 Order cancelOrder(Long orderId);
}
