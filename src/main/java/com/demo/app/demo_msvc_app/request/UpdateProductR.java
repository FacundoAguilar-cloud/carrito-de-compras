package com.demo.app.demo_msvc_app.request;

import java.math.BigDecimal;

import com.demo.app.demo_msvc_app.entities.Category;

import lombok.Data;
@Data
public class UpdateProductR {
private Long id;
private String name;
private String brand; 
private BigDecimal price;
private int inventory;
private String description;
private Category category;
}
