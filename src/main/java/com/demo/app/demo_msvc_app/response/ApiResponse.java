package com.demo.app.demo_msvc_app.response;

import lombok.AllArgsConstructor;
import lombok.Data;

//esta clase la vamos a usar para retornar info a nuestro front
@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;
}
