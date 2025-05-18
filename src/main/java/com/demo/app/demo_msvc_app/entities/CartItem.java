package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class CartItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@EqualsAndHashCode.Include
private Long id;
private int quantity;
private BigDecimal pricePerUnit;
private BigDecimal totalPrice;

@Column(nullable = false)
private boolean active;

@ManyToOne
@JoinColumn(name = "product_id") 
private Product product;


@JsonIgnore
@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "cart_id")
private Cart cart;

public void calculateTotalPrice (){

if (this.pricePerUnit == null || this.quantity <= 0) {
    throw new IllegalStateException("Price per unit and quantity must be set before calculating");
}
this.totalPrice = this.pricePerUnit.multiply(BigDecimal.valueOf(this.quantity));

if (this.cart != null)  {
this.cart.updateTotalAmount();
    
}

}

}
