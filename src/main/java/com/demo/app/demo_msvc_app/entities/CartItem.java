package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private int quantity;
private BigDecimal pricePerUnit;
private BigDecimal totalPrice;

@ManyToOne
@JoinColumn(name = "product_id") //muchos items del cart pueden pertenecer a un producto, por eso la relacion
private Product product;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "cart_id")
private Cart cart;

public void setTotalPrice (){
this.totalPrice = product.getPrice().multiply(new BigDecimal(quantity));

}


}
