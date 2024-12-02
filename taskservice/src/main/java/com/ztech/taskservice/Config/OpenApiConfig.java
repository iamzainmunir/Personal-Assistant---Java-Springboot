package com.ztech.taskservice.Config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Task Management Service API",
                version = "v1",
                description = "API for adding, viewing, and deleting tasks"
        ),
        security = @SecurityRequirement(name = "bearerAuth") // Referencing the JWT security scheme
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"  // Describes that it's a JWT token
)
public class OpenApiConfig {
    // You can add additional OpenAPI customization here, if required
}
