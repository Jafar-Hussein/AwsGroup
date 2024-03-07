package com.example.CompanyEmployee.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info (
                description = "This is a simple API for company's employees.",
                title = "Company Employee API",
                version = "1.0",
                license = @License(
                        name = "MIT-License"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:5000",
                        description = "Local server"
                ),
                @Server(
                        url = "",
                        description = "Production server"
                )
        },

        security = {
                @SecurityRequirement(
                        name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Enter your JWT token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
