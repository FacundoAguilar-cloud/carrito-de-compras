package com.demo.app.demo_msvc_app.entities;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
public class Product {
@Id
@GeneratedValue(strategy  = GenerationType.IDENTITY)
private Long id;
private String name;
private String brand; 
private BigDecimal price;
private int inventory;
private String description;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name =  "category_id")
@JsonIgnore
private Category category;

@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
private List <Image> images;

public Product(String name, String brand, BigDecimal price, int inventory, String description,
            Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
        
    }



public void decreaseInventory(int quantity){
    if (this.inventory < quantity) {
        throw new IllegalStateException("Insuficient inventory for the product: " + this.name);
    }
    this.inventory -= quantity;
}    

}
