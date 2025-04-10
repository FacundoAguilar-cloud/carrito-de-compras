package com.demo.app.demo_msvc_app.cart;

import com.demo.app.demo_msvc_app.entities.Cart;

public class CartItemService implements CartItemServiceIMPL {

    @Override
    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addItemToCart'");
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeItemFromCart'");
    }

    @Override
    public void updateItemQuantity(Long carId, int quantity, Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateItemQuantity'");
    }

}
