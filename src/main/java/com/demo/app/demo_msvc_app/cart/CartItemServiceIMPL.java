package com.demo.app.demo_msvc_app.cart;

import com.demo.app.demo_msvc_app.entities.Cart;

public interface CartItemServiceIMPL {
    Cart addItemToCart (Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long carId, int quantity, Long productId);
}
