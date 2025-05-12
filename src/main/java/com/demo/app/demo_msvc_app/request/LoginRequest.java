package com.demo.app.demo_msvc_app.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
@NotBlank
private String email;
@NotBlank
private String password;
}
