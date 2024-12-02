package com.ztech.UserService.Config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "v1",
                description = "API for managing users"
        )
)
public class OpenApiConfig {
    // You can add additional OpenAPI customization here, if required
}

