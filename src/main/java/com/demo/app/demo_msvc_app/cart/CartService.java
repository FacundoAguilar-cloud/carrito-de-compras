package com.demo.app.demo_msvc_app.cart;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.repositories.CartItemRepository;
import com.demo.app.demo_msvc_app.repositories.CartRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CartService implements CartServiceIMPL {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    

 
    @Override
    @Transactional(readOnly = true)
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
        .orElseThrow(() -> new ElementsNotFoundException("Cart not found"));
        
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
        cartRepository.delete(cart);
        

    }
    @Override
    public Long generateNewCart(){
        Cart newCart = new Cart();
        
        return cartRepository.save(newCart).getId();
    }
 
    




    
}