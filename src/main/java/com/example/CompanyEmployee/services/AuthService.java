package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.LoginResponse;
import com.example.CompanyEmployee.models.Role;
import com.example.CompanyEmployee.models.User;
import com.example.CompanyEmployee.repository.RoleRepository;
import com.example.CompanyEmployee.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Den här klassen används för att registrera en användare och logga in en användare.
 * Klassen använder UserRepository och RoleRepository för att spara användaren i databasen.
 * Klassen använder också PasswordEncoder för att kryptera användarens lösenord.
 * Klassen använder också AuthenticationManager för att autentisera användaren.
 * @Author Clara Brorson
 */
@Service
@Transactional // behövs ej?
@RequiredArgsConstructor
public class AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> register(String username, String password) {
        try {
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Username already exists");
            }

            String encryptedPassword = passwordEncoder.encode(password);

            Role userRole = roleRepository.findByAuthority("USER").get();

            Set<Role> authorities = new HashSet<>();

            authorities.add(userRole);

            User newUser = new User(0L, username, encryptedPassword, authorities);
            User savedUser = userRepository.save(newUser);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during registration");
        }
    }
    
}
