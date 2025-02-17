package pl.jagiela.microservices_ecommerce.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jagiela.microservices_ecommerce.user_service.dto.request.LoginRequest;
import pl.jagiela.microservices_ecommerce.user_service.dto.request.UserRegisterRequest;
import pl.jagiela.microservices_ecommerce.user_service.service.UserService;

@Tag(name = "Auth Controller", description = "User-related operations (registration and login)")
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user in system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Logs in the user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }
}