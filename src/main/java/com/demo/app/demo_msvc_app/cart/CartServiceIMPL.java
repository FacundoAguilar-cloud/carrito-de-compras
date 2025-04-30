package com.demo.app.demo_msvc_app.cart;

import java.math.BigDecimal;

import com.demo.app.demo_msvc_app.dto.CartDto;
import com.demo.app.demo_msvc_app.entities.Cart;



public interface CartServiceIMPL {
Cart getCartById(Long id);

BigDecimal getTotalAmount(Long id);

void clearCart(Long id);

Cart getCartByUserId(Long userId);

Long initalizeNewCart(Long userId);

void validateCartOwner(Long cartId, Long userId);


CartDto convertToDto(Cart cart);






}

