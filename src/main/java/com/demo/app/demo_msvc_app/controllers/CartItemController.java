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
import com.demo.app.demo_msvc_app.cart.CartServiceIMPL;
import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.exceptions.JwtException;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.user.UserServiceIMPL;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/cart-item")
@RequiredArgsConstructor
public class CartItemController {
private final CartItemServiceIMPL cartItemServiceIMPL;
private final CartServiceIMPL cartServiceIMPL;
private final UserServiceIMPL userService;


//ya funciona perfecto
@PostMapping("/add-item")   
public ResponseEntity <ApiResponse> addItemToCart(
    @RequestParam(required = false) Long cartId,
    @RequestParam Long userId,
    @RequestParam Long productId,
    @RequestParam Integer quantity){
    try {
        User authenticatedUser = userService.getAuthenticatedUser(); //obtenemos el usuario autentificado
        //debemos chequear que el usuario este auntentificado
        if (authenticatedUser == null) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not authenticated", null));
        }
        //si no hay un carrito, esto lo inicializa con el id del usuario autenticado
        if (cartId == null) {
            cartId= cartServiceIMPL.initalizeNewCart(authenticatedUser.getId());
        }
         //
         cartServiceIMPL.validateCartOwner(cartId, authenticatedUser.getId());
        
         //agregamos el item al carrito 
        cartItemServiceIMPL.addItemToCart(cartId, productId, quantity);
    return ResponseEntity.ok(new ApiResponse("Item added successfully", null));  

    } catch (ElementsNotFoundException e) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }  catch(JwtException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
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
