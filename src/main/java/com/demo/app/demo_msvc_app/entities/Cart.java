package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private BigDecimal totalAmount = BigDecimal.ZERO;
//con esto si eliminamos un carro, se van a eliminar todos los productos que estén dentro de el.
@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
private Set <CartItem> cartItems;
 

public void addItemToCart(CartItem cartItem) {
        if (this.cartItems == null) {
            this.cartItems = new HashSet<>();
        }
        this.cartItems.add(cartItem);
    }

public void removeItem(CartItem items){
    this.cartItems.remove(items);
    items.setCart(null);
    updateTotalAmount();

}    

private void updateTotalAmount() {
    this.totalAmount = cartItems.stream().map(item -> {
        BigDecimal unitPrice = item.getPricePerUnit();
        if (unitPrice == null) {
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
    }).reduce(BigDecimal.ZERO, BigDecimal::add);
}




}
