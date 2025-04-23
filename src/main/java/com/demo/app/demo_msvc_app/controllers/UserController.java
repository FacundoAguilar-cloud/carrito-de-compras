package com.demo.app.demo_msvc_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.exceptions.AlreadyExistExcp;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.request.NewUserR;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.user.UserServiceIMPL;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
private final UserServiceIMPL userService;

@GetMapping("/get-user-by-id/{userId}")
public ResponseEntity <ApiResponse> getUserById(@PathVariable Long userId){
    try {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse("User retrieved successfully", user));
    } catch (ElementsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
   

}

@PostMapping("/create-user")
public ResponseEntity <ApiResponse> createNewUser (NewUserR request) {
try {
    User user = userService.createUser(request);
return ResponseEntity.ok(new ApiResponse("User created successfully", user)); 
} catch (AlreadyExistExcp e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("User already exist", null));
}



}

@DeleteMapping("/delete-user/{userId}")
public ResponseEntity <ApiResponse> deleteUser(@PathVariable Long userId){
    try {
        User user = userService.getUserById(userId);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
    }
}




}
