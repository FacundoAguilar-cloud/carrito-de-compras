package com.demo.app.demo_msvc_app.request;

import java.math.BigDecimal;

import com.demo.app.demo_msvc_app.entities.Category;

//esta clase basicamente la vamos a utilizar para no trabajar sobre la entidad original, vamos a evitar posibles errores de relacion y demás.
import lombok.Data;
@Data
public class AddProductR {
private Long id;
private String name;
private String brand; 
private BigDecimal price;
private int inventory;
private String description;
private Category category;
}
