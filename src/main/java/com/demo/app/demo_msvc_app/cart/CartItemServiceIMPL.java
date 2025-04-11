package com.demo.app.demo_msvc_app.cart;



public interface CartItemServiceIMPL {
    void addItemToCart (Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long carId, int quantity, Long productId);
}
