//package pl.jagiela.microservices_ecommerce.user_service.dto.request;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Set;
//
//class UserRegisterRequestTest {
//
//    private Validator validator;
//
//    @BeforeEach
//    void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    void shouldPassValidationWhenDataIsValid() {
//        UserRegisterRequest request = new UserRegisterRequest(
//                "testUser",
//                "test@example.com",
//                "Password1!"
//        );
//
//        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
//        assertThat(violations).isEmpty();
//    }
//
//    @Test
//    void shouldFailValidationWhenUsernameIsBlank() {
//        UserRegisterRequest request = new UserRegisterRequest(
//                "",
//                "test@example.com",
//                "Password1!"
//        );
//
//        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
//        assertThat(violations).isNotEmpty();
//        assertThat(violations).anyMatch(v -> v.getMessage().equals("Username cannot be blank"));
//    }
//
//    @Test
//    void shouldFailValidationWhenEmailIsInvalid() {
//        UserRegisterRequest request = new UserRegisterRequest(
//                "testUser",
//                "invalid-email",
//                "Password1!"
//        );
//
//        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
//        assertThat(violations).isNotEmpty();
//        assertThat(violations).anyMatch(v -> v.getMessage().equals("Invalid email format"));
//    }
//
//    @Test
//    void shouldFailValidationWhenPasswordIsTooShort() {
//        UserRegisterRequest request = new UserRegisterRequest(
//                "testUser",
//                "test@example.com",
//                "Pwd1!"
//        );
//
//        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
//        assertThat(violations).isNotEmpty();
//        assertThat(violations).anyMatch(v -> v.getMessage().equals("Password must be at least 8 characters long"));
//    }
//}