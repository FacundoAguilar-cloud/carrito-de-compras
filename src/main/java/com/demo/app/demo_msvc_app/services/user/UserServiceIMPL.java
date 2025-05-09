package com.demo.app.demo_msvc_app.services.user;

import com.demo.app.demo_msvc_app.dto.UserDto;
import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.request.NewUserR;
import com.demo.app.demo_msvc_app.request.UpdateUserR;

public interface UserServiceIMPL {

User getUserById(Long userId);

User createUser(NewUserR request);

User updateUser(UpdateUserR  request, Long userId);

void deleteUserById(Long userId);

UserDto convertToDto(User user);

User getAuthenticatedUser();

}
