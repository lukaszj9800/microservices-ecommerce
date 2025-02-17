//package pl.jagiela.microservices_ecommerce.user_service.security;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import pl.jagiela.microservices_ecommerce.user_service.security.JwtService;
//
//class JwtServiceTest {
//
//    @InjectMocks
//    private JwtService jwtService;
//
//    private String testUsername = "testUser";
//
//    @BeforeEach
//    void setUp() {
//        jwtService = new JwtService();
//    }
//
//    @Test
//    void shouldGenerateToken() {
//        String token = jwtService.generateToken(testUsername);
//
//        assertThat(token).isNotNull();
//        assertThat(jwtService.extractUsername(token)).isEqualTo(testUsername);
//    }
//
//    @Test
//    void shouldValidateCorrectToken() {
//        String token = jwtService.generateToken(testUsername);
//
//        boolean isValid = jwtService.isTokenValid(token, testUsername);
//
//        assertThat(isValid).isTrue();
//    }
//}
