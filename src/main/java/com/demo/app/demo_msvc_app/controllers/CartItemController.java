package com.demo.app.demo_msvc_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.app.demo_msvc_app.cart.CartItemServiceIMPL;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartItemController {
private final CartItemServiceIMPL cartItemServiceIMPL;

@PostMapping("/add-item-to-cart")
public ResponseEntity <ApiResponse> addItemToCart(
    @RequestParam Long cartId,
    @RequestParam Long productId,
    @RequestParam Integer quantity){
    try {
        cartItemServiceIMPL.addItemToCart(cartId, productId, quantity);
    return ResponseEntity.ok(new ApiResponse("Item added successfully", null));  

    } catch (ElementsNotFoundException e) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }    
    
}

@DeleteMapping("/remove-item-from-cart")
public ResponseEntity <ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId){
 try {
    cartItemServiceIMPL.removeItemFromCart(cartId, productId);
    return ResponseEntity.ok(new ApiResponse("Item removed successfully", null));  
 } catch (ElementsNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
 }
 
}

@PutMapping("path/{id}")
public ResponseEntity <ApiResponse> updateItemQuantity(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer quantity ) {
    return ResponseEntity.ok(new ApiResponse(null, quantity));
}

}
