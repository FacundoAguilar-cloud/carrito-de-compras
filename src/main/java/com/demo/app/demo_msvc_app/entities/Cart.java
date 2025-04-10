package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;
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
}
