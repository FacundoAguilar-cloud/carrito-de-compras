package com.demo.app.demo_msvc_app.dto;

import java.math.BigDecimal;
import java.util.List;

import com.demo.app.demo_msvc_app.entities.Category;
import com.demo.app.demo_msvc_app.entities.Image;
import lombok.Data;


@Data
public class ProductDto {
private Long id;
private String name;
private String brand; 
private BigDecimal price;
private int inventory;
private String description;
private Category category;
private List <Image> images;
}
