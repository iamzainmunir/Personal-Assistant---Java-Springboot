package com.ztech.UserService.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResponse {

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Role of the user in the system", example = "ADMIN")
    private String role;

    @Schema(description = "JWT token for user authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.Sl9pEohdhwVt8cQjJb63PFTUJdUMz5u9ptPU5q0KwQo")
    private String token;

    // Constructor
    public UserResponse(String username, String email, String role, String token) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}
