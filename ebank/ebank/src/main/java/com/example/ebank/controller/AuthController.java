package com.example.ebank.controller;

import com.example.ebank.entity.Client;
import com.example.ebank.entity.Role;
import com.example.ebank.repository.RoleRepository;
import com.example.ebank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("User authenticated successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (clientService.findClientByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        Client client = new Client();
        client.setName(registerRequest.getName());
        client.setEmail(registerRequest.getEmail());
        client.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role clientRole = roleRepository.findByName("CLIENT");
        if (clientRole == null) {
            clientRole = new Role();
            clientRole.setName("CLIENT");
            roleRepository.save(clientRole);
        }
        roles.add(clientRole);
        client.setRoles(roles);

        clientService.saveClient(client);
        return ResponseEntity.ok("User registered successfully");
    }
}

class LoginRequest {
    private String email;
    private String password;

    // getters and setters
}

class RegisterRequest {
    private String name;
    private String email;
    private String password;

    // getters and setters
}
