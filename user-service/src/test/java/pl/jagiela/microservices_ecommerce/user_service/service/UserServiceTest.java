//package pl.jagiela.microservices_ecommerce.user_service.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.server.ResponseStatusException;
//import pl.jagiela.microservices_ecommerce.user_service.dto.request.LoginRequest;
//import pl.jagiela.microservices_ecommerce.user_service.dto.request.UserRegisterRequest;
//import pl.jagiela.microservices_ecommerce.user_service.dto.response.AuthResponse;
//import pl.jagiela.microservices_ecommerce.user_service.model.User;
//import pl.jagiela.microservices_ecommerce.user_service.repository.UserRepository;
//import pl.jagiela.microservices_ecommerce.user_service.security.JwtService;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private JwtService jwtService;
//
//    @Mock
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void shouldRegisterUserSuccessfully() {
//        UserRegisterRequest request = new UserRegisterRequest("testUser", "test@example.com", "Password1!");
//
//        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
//        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
//
//        String result = userService.register(request);
//
//        assertThat(result).isEqualTo("User registered successfully");
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void shouldThrowExceptionIfUserAlreadyExists() {
//        UserRegisterRequest request = new UserRegisterRequest("existingUser", "existing@example.com", "Password1!");
//        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));
//
//        assertThatThrownBy(() -> userService.register(request))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("Username already exists");
//
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    void shouldLoginSuccessfully() {
//        LoginRequest request = new LoginRequest("testUser", "Password1!");
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("encodedPassword");
//
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("Password1!", "encodedPassword")).thenReturn(true);
//        when(jwtService.generateToken("testUser")).thenReturn("mocked-token");
//
//        AuthResponse response = userService.loginUser(request);
//
//        assertThat(response.getToken()).isEqualTo("mocked-token");
//    }
//
//    @Test
//    void shouldThrowExceptionWhenUserNotFound() {
//        LoginRequest request = new LoginRequest("wrongUser", "Password1!");
//
//        when(userRepository.findByUsername("wrongUser")).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> userService.loginUser(request))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("User not found");
//    }
//
//    @Test
//    void shouldThrowExceptionWhenPasswordIsIncorrect() {
//        LoginRequest request = new LoginRequest("testUser", "WrongPassword!");
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("encodedPassword");
//
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("WrongPassword!", "encodedPassword")).thenReturn(false);
//
//        assertThatThrownBy(() -> userService.loginUser(request))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("Invalid credentials");
//    }
//}