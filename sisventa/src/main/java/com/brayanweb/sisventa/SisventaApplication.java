package com.brayanweb.sisventa;

import com.brayanweb.sisventa.dtos.UserRequest;
import com.brayanweb.sisventa.models.Role;
import com.brayanweb.sisventa.repositories.UserRepository;
import com.brayanweb.sisventa.services.RoleService;
import com.brayanweb.sisventa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SisventaApplication implements CommandLineRunner {
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(SisventaApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        Role role = roleService.createRoleIfNotFound("Admin");
        roleService.createRoleIfNotFound("Client");
        
        if (!userRepository.findByEmail("brayan@gmail.com").isPresent()) {
            UserRequest user = new UserRequest();
            user.setEmail("brayan@gmail.com");
            user.setPassword("brayan123");
            user.setRole_id(role.getId());
            userService.create(user);
        }
        
    }
    
}
