package com.demo.app.demo_msvc_app.cart;

import java.math.BigDecimal;


import com.demo.app.demo_msvc_app.entities.Cart;

public interface CartServiceIMPL {
Cart getCartById(Long id);

BigDecimal getTotalAmount(Long id);

void clearCart(Long id);

Long generateNewCart();

Cart getCartByUserId(Long userId);



}

