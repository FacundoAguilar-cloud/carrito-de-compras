package com.demo.app.demo_msvc_app.controllers;


import java.math.BigDecimal;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.demo_msvc_app.cart.CartServiceIMPL;
import com.demo.app.demo_msvc_app.dto.CartDto;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.response.ApiResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
private final CartServiceIMPL cartServiceIMPL;
//funciona correctamente con el cambio del DTO
@GetMapping("/get-cart-by-id/{cartId}")
public ResponseEntity<ApiResponse> getCartById(@PathVariable Long cartId) {
    try {   
        Cart cart = cartServiceIMPL.getCartById(cartId);
        CartDto cartDto = cartServiceIMPL.convertToDto(cart);
        return ResponseEntity.ok(new ApiResponse("Cart retrieved successfully", cartDto));
            
            
    } catch (ElementsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    }
}
//funciona perfecto
@DeleteMapping("/clear-cart-by-id/{cartId}")
@Transactional
public ResponseEntity <ApiResponse> clearCart(@PathVariable Long cartId){
try {
    cartServiceIMPL.clearCart(cartId);
    return ResponseEntity.ok(new ApiResponse("Cart cleared successfully", null));
} catch (ElementsNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
    .body(new ApiResponse("The cart does not exist or has already been deleted " , null));
}
 
}
//todo ok
@GetMapping("/get-total-amount/{cartId}")
public ResponseEntity <ApiResponse> getTotalAmount(@PathVariable Long cartId){
    try {
        BigDecimal totalPrice = cartServiceIMPL.getTotalAmount(cartId);
        return ResponseEntity.ok(new ApiResponse("Total amount retrieved successfully", totalPrice));
    } catch (ElementsNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The total amount could not be found, please check the cart ID", null));
    }
    

}



}
