package com.demo.app.demo_msvc_app.cart;


import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.entities.CartItem;
import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.exceptions.CartLogicException;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.repositories.CartItemRepository;
import com.demo.app.demo_msvc_app.repositories.CartRepository;
import com.demo.app.demo_msvc_app.repositories.ProductRepository;
import com.demo.app.demo_msvc_app.services.images.products.category.ProductServiceIMPL;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional

public class CartItemService implements CartItemServiceIMPL {
    private final  CartItemRepository cartItemRepository;
    private final ProductServiceIMPL productServiceIMPL;
    private final CartServiceIMPL cartServiceIMPL;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        
        //0 nos aseguramos que la cantidad nunca sea menor o igual a cero
        if (quantity <= 0) {
        throw new IllegalArgumentException("Quantity must be positive");
    }
       
        //1 vamos a asegurarnos que tanto el carro como el producto existan
        Cart cart = cartRepository.findById(cartId)
        .orElseThrow(()-> new ElementsNotFoundException("Cart not found"));
       //2 obtener el producto
        Product product = productRepository.findById(productId)
        .orElseThrow(()-> new ElementsNotFoundException("Product not found"));
       //3 chequear si el producto ya está en el carro

       boolean alreadyHaveProduct= cartItemRepository.existsByCartIdAndProductId(cartId, productId);

       if (alreadyHaveProduct) {
        throw new CartLogicException("Product is already in cart, you can modify the quantity by other method");
       }

       CartItem newItem = new CartItem();
       newItem.setCart(cart);
       newItem.setProduct(product);
       newItem.setQuantity(quantity);
       newItem.setPricePerUnit(product.getPrice());
       newItem.calculateTotalPrice();
    
       cart.addItemToCart(newItem);

        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartServiceIMPL.getCartById(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, int quantity, Long productId) {
        Cart cart = cartServiceIMPL.getCartById(cartId);
        cart.getCartItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .ifPresent(item ->{
            item.setQuantity(quantity);
            item.setPricePerUnit(item.getProduct().getPrice());
            item.calculateTotalPrice();
        });
       
        cartRepository.save(cart);
    }
    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartServiceIMPL.getCartById(cartId);

        return cart.getCartItems()
        .stream()
        .filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new NoSuchElementException("Item not found"));
        
    }

}
