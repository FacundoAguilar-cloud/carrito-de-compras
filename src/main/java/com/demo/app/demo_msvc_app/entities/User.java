package com.demo.app.demo_msvc_app.entities;

import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long  id;
private String firstname;
private String Lastname;
private String email;
private String password;
@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private Cart cart;
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List <Order> order;

}
