package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class OrderItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@EqualsAndHashCode.Include
@ToString.Include
private Long id;

@ManyToOne
@JoinColumn(name =  "order_id")
@JsonIgnore
@ToString.Exclude
private Order order;

@ManyToOne
@JoinColumn(name =  "productId")
private Product product;
private int quantity;
private BigDecimal price;


public OrderItem(int quantity, BigDecimal price, Order order, Product product) {
    this.quantity = quantity;
    this.price = price;
    this.order = order;
    this.product = product;
}



}



