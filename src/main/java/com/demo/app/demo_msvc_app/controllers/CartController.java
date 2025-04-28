package com.demo.app.demo_msvc_app.controllers;


import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
//ESTE FUNCA
@GetMapping("/get-cart-by-id/{cartId}")
public ResponseEntity<ApiResponse> getCartById(@PathVariable Long cartId) {
    try {   
        Cart cart = cartServiceIMPL.getCartById(cartId);
        return ResponseEntity.ok(
            new ApiResponse(
                "Cart retrieved successfully", 
                Map.of(
                    "cart", cart,
                    "metadata", Map.of(
                        "itemCount", cart.getCartItems().size(),
                        "totalAmount", cart.getTotalAmount()
                    )
                )
            )
        );
    } catch (ElementsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), null));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Error retrieving cart: " + e.getMessage(), null));
    }
}
//revisar despues, hay algo que no deja vaciar los carros, da una excepcion
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
