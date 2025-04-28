package com.demo.app.demo_msvc_app.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.app.demo_msvc_app.cart.CartItemServiceIMPL;
import com.demo.app.demo_msvc_app.cart.CartServiceIMPL;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.exceptions.AlreadyExistExcp;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.user.UserServiceIMPL;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartItemController {
private final CartItemServiceIMPL cartItemServiceIMPL;
private final CartServiceIMPL cartServiceIMPL;
private final UserServiceIMPL userService;


//este es nuevo asi que hay que volver a probarlo
@PostMapping("/create-cart/{userId}")
public ResponseEntity <ApiResponse> createCar (@PathVariable Long userId) {
    try { Cart cart = cartServiceIMPL.createCart(userId);
        return ResponseEntity.ok(
            new ApiResponse(
                "Cart created successfully",
                Map.of("cartId", cart.getId(), "userId", cart.getUserId())
            )
        );
        
    } catch (AlreadyExistExcp e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Cart already exist", null));
    }
}


@PostMapping("/add-item-to-cart")
public ResponseEntity <ApiResponse> addItemToCart(
    @RequestParam Long userId, 
    @RequestParam Long productId,
    @RequestParam Integer quantity){
    try {
          User user = userService.getUserById(userId);
          Cart cart = cartServiceIMPL.getOrCreateCart(user); //Esto va a verificar la existencia del carro
          
        
        cartItemServiceIMPL.addItemToCart(cart.getId(), productId, quantity);
    return ResponseEntity.ok(new ApiResponse("Item added successfully", null));  

    } catch (ElementsNotFoundException e) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }    
    
}
//funca
@DeleteMapping("/remove-item-from-cart/{itemId}/{cartId}")
public ResponseEntity <ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
 try {
    cartItemServiceIMPL.removeItemFromCart(cartId, itemId);
    return ResponseEntity.ok(new ApiResponse("Item removed successfully", null));  
 } catch (ElementsNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
 }
 
}
//funciona ok
@PutMapping("/update-item-quantity/{cartId}/{itemId}")
public ResponseEntity <ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Integer quantity ) {
    try {
        cartItemServiceIMPL.updateItemQuantity(cartId, quantity, itemId);

        return ResponseEntity.ok(new ApiResponse("Updated item successfully", null));
    } 
    catch (ElementsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
    
}

}
