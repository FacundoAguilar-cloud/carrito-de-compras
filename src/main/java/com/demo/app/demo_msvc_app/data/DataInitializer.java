package com.demo.app.demo_msvc_app.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.demo.app.demo_msvc_app.entities.Role;
import com.demo.app.demo_msvc_app.entities.User;
import com.demo.app.demo_msvc_app.repositories.RoleRepository;
import com.demo.app.demo_msvc_app.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component 
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DataInitializer implements ApplicationListener <ApplicationReadyEvent> {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final RoleRepository roleRepository;
private final EntityManager entityManager;

@Override //aca creamos los roles
public void onApplicationEvent(ApplicationReadyEvent event) {
    Set <String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
    createDefaultRoleIfNotExist(defaultRoles);
    createDefaultUserIfNotExist();
    createDefaultAdminIfNotExist();
}



private void createDefaultUserIfNotExist(){
     // 1. Obtener el rol con entityManager para mantenerlo managed
        Role userRole = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name = 'ROLE_USER'", Role.class)
                .getSingleResult();

        // 2. Crear usuarios
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "email.com";
            
            if (userRepository.existsByEmail(defaultEmail)) continue;

            User user = new User();
            user.setFirstName("User");
            user.setLastName("User " + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            
            // 3. Asegurar que la relación es managed
            user.setRoles(Set.of(entityManager.merge(userRole)));
            
            userRepository.save(user);
            log.info("Usuario creado: {}", defaultEmail);
    }
}
    


private void createDefaultAdminIfNotExist(){
      Role  adminRole = roleRepository.findRoleByName("ROLE_ADMIN").get();
    for(int i = 1; i <= 2; i++ ){
        String defaultEmail = "admin" +i+"email.com";
        
        if (userRepository.existsByEmail(defaultEmail)) {
            continue;
        }
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Admin "+ i);
        user.setEmail(defaultEmail);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(Set.of(adminRole));
        userRepository.save(user);

        System.out.println("Admin Created Successfully");
    }
}










private void createDefaultRoleIfNotExist(Set <String> roles){
    roles.stream()
    .filter(role -> roleRepository.findRoleByName(role).isEmpty())
    .map(Role:: new).forEach(roleRepository::save);
}


}

