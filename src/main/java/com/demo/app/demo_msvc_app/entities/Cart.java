package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Cart {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@EqualsAndHashCode.Include
@ToString.Include
private Long id;
private BigDecimal totalAmount = BigDecimal.ZERO;
@Version
private Integer version;
//con esto si eliminamos un carro, se van a eliminar todos los productos que estén dentro de el.
@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
private Set <CartItem> cartItems = new HashSet<>();
 //esto vendria a ser una especie de "getter seguro" lo cual nos va a hacer evitar el NPE

 @OneToOne(fetch = FetchType.LAZY)  
@JoinColumn(name = "user_id")
@ToString.Exclude
@JsonIgnore
private User user;



public Set<CartItem> getCartItems() {
    if (this.cartItems == null) {
        this.cartItems = new HashSet<>();
    }
    return this.cartItems;
}


public void addItemToCart(CartItem cartItem) {
        getCartItems().add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
    }

public void removeItem(CartItem items){
    getCartItems().remove(items);
    items.setCart(null);
    updateTotalAmount();

}    

public void updateTotalAmount() {
    this.totalAmount = this.cartItems.stream()
    .map(item -> {
        // Fuerza la actualización del precio si es necesario
        if (item.getTotalPrice() == null) {
            item.setTotalPrice();
        }
        return item.getTotalPrice();
    })
    .reduce(BigDecimal.ZERO, BigDecimal::add);
}





}
