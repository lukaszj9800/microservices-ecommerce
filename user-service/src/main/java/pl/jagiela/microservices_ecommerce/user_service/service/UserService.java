package pl.jagiela.microservices_ecommerce.user_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jagiela.microservices_ecommerce.user_service.dto.request.LoginRequest;
import pl.jagiela.microservices_ecommerce.user_service.dto.request.UserRegisterRequest;
import pl.jagiela.microservices_ecommerce.user_service.dto.response.AuthResponse;
import pl.jagiela.microservices_ecommerce.user_service.model.User;
import pl.jagiela.microservices_ecommerce.user_service.repository.UserRepository;
import pl.jagiela.microservices_ecommerce.user_service.security.JwtService;
import pl.jagiela.microservices_ecommerce.user_service.security.PasswordValidator;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String register(UserRegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        PasswordValidator.validatePassword(request.getPassword());

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    public boolean ifUserExist(String username) {
        return userRepository.existsByUsername(username);
    }
}