package com.example.CompanyEmployee.config;

import com.example.CompanyEmployee.utils.KeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Detta är konfigurationsklassen för säkerhet.
 * Den hanterar konfigurationen för webbsäkerhet.
 * @author Fredrik
 */
@Configuration
public class SecurityConfig {

    private final KeyProperties keys;

    /**
     * Konstruktor för SecurityConfig.
     * @param keys Nycklarna som används för att skapa och verifiera JWTs.
     */
    public SecurityConfig(KeyProperties keys) {
        this.keys = keys;
    }


    private static final String[] SWAGGER_WHITE_LIST = {"/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"};


    /**
     * Denna metod konfigurerar säkerhetsfilterkedjan.
     * @param http HttpSecurity-objektet.
     * @return En säkerhetsfilterkedja.
     * @throws Exception Om ett fel uppstår under konfigurationen.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET, SWAGGER_WHITE_LIST).permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/user/**").hasRole("ADMIN");
                    auth.requestMatchers("/city/**").hasRole("ADMIN");
                    auth.requestMatchers("/company/**").hasRole("ADMIN");
                    auth.requestMatchers("/employee/**").hasAnyRole("ADMIN", "USER");
                    auth.anyRequest().authenticated();
                });

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    /**
     * Denna metod skapar en JwtDecoder.
     * @return En JwtDecoder.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.keys.getPublicKey()).build();
    }

    /**
     * Denna metod skapar en PasswordEncoder.
     * @return En PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Denna metod skapar en AuthenticationManager.
     * @param detailService UserDetailsService-objektet.
     * @return En AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    /**
     * Denna metod skapar en JwtEncoder.
     * @return En JwtEncoder.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Denna metod skapar en JwtAuthenticationConverter.
     * @return En JwtAuthenticationConverter.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
}

