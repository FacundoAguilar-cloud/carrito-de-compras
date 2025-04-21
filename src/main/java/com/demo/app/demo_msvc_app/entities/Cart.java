package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cartItems"})
public class Cart {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private BigDecimal totalAmount = BigDecimal.ZERO;
@Version
private Integer version = 0;
//con esto si eliminamos un carro, se van a eliminar todos los productos que estén dentro de el.
@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
private Set <CartItem> cartItems = new HashSet<>();
 //esto vendria a ser una especie de "getter seguro" lo cual nos va a hacer evitar el NPE
@OneToOne
@JoinColumn(name = "user_id")
 private User user;



public void addItemToCart(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
    }

public void removeItem(CartItem items){
    this.cartItems.remove(items);
    items.setCart(null);
    updateTotalAmount();

}    

public void updateTotalAmount() {
    this.totalAmount = cartItems.stream().map(item -> {
        BigDecimal unitPrice = item.getPricePerUnit();
        if (unitPrice == null) {
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
    }).reduce(BigDecimal.ZERO, BigDecimal::add);
}




}
