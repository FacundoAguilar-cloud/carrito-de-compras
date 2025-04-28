package com.demo.app.demo_msvc_app.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component //un bean de spring 
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener <ApplicationReadyEvent> {
private final UserRepository userRepository;

@Override
public void onApplicationEvent(ApplicationReadyEvent event) {
    createDefaultUserIfNotExist();
}



private void createDefaultUserIfNotExist(){
    for(int i = 1; i <= 5; i++ ){
        String defaultEmail = "user" +i+"email.com";
        
        if (userRepository.existsByEmail(defaultEmail)) {
            continue;
        }
        User user = new User();
        user.setFirstName("User");
        user.setLastName("User "+ i);
        user.setEmail(defaultEmail);
        user.setPassword("654321");
        userRepository.save(user);

        System.out.println("User Created Successfully");
    }
}

}
