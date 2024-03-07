package com.example.CompanyEmployee.controller;

import com.example.CompanyEmployee.models.LoginResponse;
import com.example.CompanyEmployee.models.RegistrationPayload;
import com.example.CompanyEmployee.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * AuthController är en klass som hanterar autentisering av användare.
 * Klassen innehåller två metoder, register och login.
 * Register används för att registrera en användare.
 * Login används för att logga in en användare.
 * Klassen använder sig av AuthService för att hantera autentiseringen.
 * @Author Clara Brorson
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationPayload payload) {

        return authService.register(payload.getUsername(), payload.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody RegistrationPayload payload) {

        return authService.login(payload.getUsername(), payload.getPassword());
    }

}
