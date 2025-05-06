package com.demo.app.demo_msvc_app.response;

import com.demo.app.demo_msvc_app.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class canceledOrderResponse {
private String message;
private String status;
private Object data;

public canceledOrderResponse(String status, Order order){
    this.message = "Order canceled";
    this.status = status;
    this.data = order;
}

}
