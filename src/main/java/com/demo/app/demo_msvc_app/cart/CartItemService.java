package com.demo.app.demo_msvc_app.cart;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import com.demo.app.demo_msvc_app.entities.Cart;
import com.demo.app.demo_msvc_app.entities.CartItem;
import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.repositories.CartItemRepository;
import com.demo.app.demo_msvc_app.repositories.CartRepository;
import com.demo.app.demo_msvc_app.services.images.products.category.ProductServiceIMPL;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceIMPL {
    private final  CartItemRepository cartItemRepository;
    private final ProductServiceIMPL productServiceIMPL;
    private final CartServiceIMPL cartServiceIMPL;
    private final CartRepository cartRepository;
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
       //1 obtener el carro
        Cart cart = cartServiceIMPL.getCartById(cartId);
       //2 obtener el producto
        Product product = productServiceIMPL.getProductById(productId);
       //3 chequear si el producto ya está en el carro
       CartItem cartItem = cart
       .getCartItems()
       .stream()
       .filter(item -> item.equals(productId)).findFirst().orElse(new CartItem());
       //4 si está, incrementa la cantidad según se necesite
       if (cartItem.getId() == null) {
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPricePerUnit(product.getPrice());
        
    }
       else{
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
       }
       cartItem.setTotalPrice();
       cart.addItemToCart(cartItem);
       cartItemRepository.save(cartItem);
       cartRepository.save(cart);

       //5 si no está, entonces "inciar" una entrada nueva de item al carro
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartServiceIMPL.getCartById(cartId);
        CartItem itemToRemove = cart
        .getCartItems()
        .stream()
        .filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new NoSuchElementException("Product not found"));
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long carId, int quantity, Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateItemQuantity'");
    }

}
