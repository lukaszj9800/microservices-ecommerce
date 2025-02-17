//package pl.jagiela.microservices_ecommerce.user_service.exception;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.server.ResponseStatusException;
//import pl.jagiela.microservices_ecommerce.user_service.dto.response.ValidationErrorResponse;
//
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class GlobalExceptionHandlerTest {
//
//    private GlobalExceptionHandler exceptionHandler;
//
//    @BeforeEach
//    void setUp() {
//        exceptionHandler = new GlobalExceptionHandler();
//    }
//
//    @Test
//    void shouldHandleResponseStatusException() {
//        ResponseStatusException ex = new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
//
//        ResponseEntity<ValidationErrorResponse> response = exceptionHandler.handleResponseStatusException(ex);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
//        assertThat(response.getBody()).isInstanceOf(Map.class);
//    }
//}
