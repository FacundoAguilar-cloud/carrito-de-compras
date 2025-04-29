package com.demo.app.demo_msvc_app.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
@Id
@GeneratedValue(strategy =  GenerationType.IDENTITY)
private Long id;
private String name;
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List <Product> products;

public Category(String name) {
    this.name = name;
}
}
