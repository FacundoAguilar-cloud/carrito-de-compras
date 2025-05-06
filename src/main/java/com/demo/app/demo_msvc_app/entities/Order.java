package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.demo.app.demo_msvc_app.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@ToString(onlyExplicitlyIncluded = true) 
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@EqualsAndHashCode.Include
@ToString.Include
private Long orderId;

private boolean deleted = false;

private LocalDate orderDate;

private BigDecimal totalOrderAmount;

@Enumerated(EnumType.STRING)
private OrderStatus orderStatus;

@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
@ToString.Exclude
private Set <OrderItem> orderItems = new HashSet<>();

@ManyToOne
@JoinColumn(name = "user_id")
@JsonIgnore
@ToString.Exclude
private User user;

@PreRemove
public void softDelete(){
this.deleted = true;
this.orderStatus = orderStatus.CANCELLED;
}







}

