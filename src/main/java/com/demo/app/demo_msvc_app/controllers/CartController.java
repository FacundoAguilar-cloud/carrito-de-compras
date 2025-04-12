package com.demo.app.demo_msvc_app.controllers;


import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.demo_msvc_app.cart.CartServiceIMPL;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.response.ApiResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {
private final CartServiceIMPL cartServiceIMPL;
@GetMapping("/get-cart-by-id/{cartId}")
public ResponseEntity <ApiResponse> getCart(@PathVariable Long cartId){
try {
    Cart cart = cartServiceIMPL.getCartById(cartId);
    return ResponseEntity.ok(new ApiResponse("cart retrieved successfully", cart));
} catch (ElementsNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart not found, please try again", null));
}
   
}
@DeleteMapping("/clear-cart-by-id/{cartId}")
public ResponseEntity <ApiResponse> clearCart(@PathVariable Long cartId, @RequestBody Cart cart){
cartServiceIMPL.clearCart(cartId);
return ResponseEntity.ok(new ApiResponse("Cart cleared successfully", null));
}

@GetMapping("/get-total-amount/{cartId}")
public ResponseEntity <ApiResponse> getTotalAmount(@PathVariable Long cartId){
    try {
        BigDecimal totalPrice = cartServiceIMPL.getTotalAmount(cartId);
        return ResponseEntity.ok(new ApiResponse("Total amount retrieved successfully", totalPrice));
    } catch (ElementsNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
    

}



}
