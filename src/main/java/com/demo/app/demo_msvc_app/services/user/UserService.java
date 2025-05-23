package com.demo.app.demo_msvc_app.services.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.app.demo_msvc_app.dto.UserDto;
import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.exceptions.AlreadyExistExcp;
import com.demo.app.demo_msvc_app.exceptions.ElementsNotFoundException;
import com.demo.app.demo_msvc_app.repositories.UserRepository;
import com.demo.app.demo_msvc_app.request.NewUserR;
import com.demo.app.demo_msvc_app.request.UpdateUserR;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceIMPL {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
        .orElseThrow(() -> new ElementsNotFoundException("User not found, please try again"));
    }

    @Override
    public User createUser(NewUserR request) {
       return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail()))
       .map(req -> {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userRepository.save(user);
       }).orElseThrow(() -> new AlreadyExistExcp("User already exist"));
    }

    @Override
    public User updateUser(UpdateUserR request, Long userId) {
        return userRepository.findById(userId).map(existinUser -> {
            existinUser.setFirstName(request.getFirstName());
            existinUser.setLastName(request.getLastName());
            return userRepository.save(existinUser);
        }).orElseThrow(() -> new ElementsNotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, ()-> new ElementsNotFoundException("User not found"));
    }
    public UserDto convertToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();

       return userRepository.findByEmail(email);


    
    }



}
