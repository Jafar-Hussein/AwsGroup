package com.example.CompanyEmployee.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class TokenServiceTest {

    @Mock
    JwtEncoder jwtEncoder;

    @Mock
    JwtDecoder jwtDecoder;

    @InjectMocks
    TokenService tokenService;

    @Mock
    AuthService authService;

    @Mock
    AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateJwt() {
        // Arrange
        String username = "user";
        String password = "password";

        // Create a mock authentication object
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

        // Mock the behavior of the authentication manager to return the mock authentication object
        when(authenticationManager.authenticate(auth)).thenReturn(auth);

        // Mock the behavior of the JwtEncoder to return a mock JWT
        Jwt mockJwt = Jwt.withTokenValue("mockToken")
                .header("alg", "HS256")
                .header("typ", "JWT")
                .claim("sub", username)
                .claim("roles", Collections.singletonList("USER"))
                .build();
        when(jwtEncoder.encode(any())).thenReturn(mockJwt);

        // Act
        String actualToken = tokenService.generateJwt(auth);

        // Assert
        assertNotNull(actualToken);
        assertEquals("mockToken", actualToken);
    }
}


