package com.demo.app.demo_msvc_app.cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.repositories.CartItemRepository;
import com.demo.app.demo_msvc_app.repositories.CartRepository;
import com.demo.app.demo_msvc_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CartService implements CartServiceIMPL {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    

 
    @Override
    @Transactional(readOnly = true)
    public Cart getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
        .orElseThrow(() -> new ElementsNotFoundException("Cart not found"));

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
    @Transactional
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);

    }
    
    
    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findById(userId)
        .orElseThrow(() -> new ElementsNotFoundException("Cart by userId not found, please try again"));
    }

    @Override
    public Long initalizeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }

   




    
}