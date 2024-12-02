package com.ztech.UserService.Controller;

import com.ztech.UserService.DTO.ErrorResponse;
import com.ztech.UserService.DTO.LoginRequest;
import com.ztech.UserService.DTO.RegisterRequest;
import com.ztech.UserService.DTO.UserResponse;
import com.ztech.UserService.Exception.BadCredentialsException;
import com.ztech.UserService.Exception.DuplicateEmailException;
import com.ztech.UserService.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for user management operations like registration and login.")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user", description = "Allows a user to register with email, username, and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email or Username already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        try {
            UserResponse userResponse = userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (DuplicateEmailException ex) {
            // Handle duplicate email exception
            response.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
            return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body(
                    new ErrorResponse("Email or Username already exists", "409")
            );
        } catch (Exception ex) {
            // Handle any other unexpected exception
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse("An unexpected error occurred", "500")
            );
        }
    }

    @Operation(summary = "Login a user", description = "Authenticates a user based on username and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid username or password",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            UserResponse userResponse = userService.login(request);
            return ResponseEntity.ok(userResponse);
        } catch (BadCredentialsException ex) {
            // Handle invalid username or password exception
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(
                    new ErrorResponse("Invalid username or password", "401")
            );
        } catch (Exception ex) {
            // Handle any other unexpected exception
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(
                    new ErrorResponse("An unexpected error occurred", "500")
            );
        }
    }
}
