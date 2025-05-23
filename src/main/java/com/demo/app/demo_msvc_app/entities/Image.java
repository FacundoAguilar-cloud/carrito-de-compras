package com.demo.app.demo_msvc_app.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String fileName;
private String fileType;
@Lob
private byte[] image;
private String downloadUrl;
@ManyToOne
@JoinColumn(name= "product_id")
private Product product;
}
