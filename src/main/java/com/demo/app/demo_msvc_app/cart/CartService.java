package com.demo.app.demo_msvc_app.cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.repositories.CartItemRepository;
import com.demo.app.demo_msvc_app.repositories.CartRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CartService implements CartServiceIMPL {
    private final CartItemServiceIMPL cartItemServiceIMPL;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    CartService(CartItemService cartItemService) {
        this.cartItemServiceIMPL = cartItemService;
        this.cartRepository = null;
        this.cartItemRepository = null;
    }
    @Override
    public Cart getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cart not found, please try again"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalAmount(Long id) {
       Cart cart = getCartById(id);
        return cart.getTotalAmount();
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);

    }
    @Override
    public Long generateNewCart(){
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }
 
    




    
}