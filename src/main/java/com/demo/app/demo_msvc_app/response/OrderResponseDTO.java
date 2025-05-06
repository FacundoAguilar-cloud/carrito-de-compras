package com.demo.app.demo_msvc_app.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import com.demo.app.demo_msvc_app.dto.OrderItemDTO;
import com.demo.app.demo_msvc_app.entities.Order;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private String message;
    private OrderDataDTO data;  // Clase interna para los datos de la orden

    // Si todo sale bien:
    public OrderResponseDTO(String message, Order order) {
        this.message = message;
        this.data = new OrderDataDTO(order);
    }

    // Si algo sale mal:
    public OrderResponseDTO(String message, String error) {
        this.message = message;
        this.data = new OrderDataDTO(error);
    }

    // clase con los datos que vamos a necesitar
    @Data
    private static class OrderDataDTO {
        private Long orderId;
        private LocalDate orderDate;
        private BigDecimal totalOrderAmount;
        private String orderStatus;
        private List<OrderItemDTO> orderItems;
        private String error;

        // Constructor para éxito (con Order)
        public OrderDataDTO(Order order) {
            this.orderId = order.getOrderId();
            this.orderDate = order.getOrderDate();
            this.totalOrderAmount = order.getTotalOrderAmount();
            this.orderStatus = order.getOrderStatus().name();
            this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDTO::new)
                .toList();
        }

        // Constructor para error (con String)
        public OrderDataDTO(String error) {
            this.error = error;
        }
    }
}







