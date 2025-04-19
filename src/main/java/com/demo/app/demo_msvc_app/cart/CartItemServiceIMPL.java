package com.demo.app.demo_msvc_app.cart;

import com.demo.app.demo_msvc_app.entities.CartItem;

public interface CartItemServiceIMPL {
    void addItemToCart (Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long cartId, int quantity, Long productId);

    CartItem getCartItem(Long cartId, Long productId);
}
