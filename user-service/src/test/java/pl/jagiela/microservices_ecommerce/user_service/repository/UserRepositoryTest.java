//package pl.jagiela.microservices_ecommerce.user_service.repository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import pl.jagiela.microservices_ecommerce.user_service.model.User;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//    }
//
//    @Test
//    void shouldFindUserByUsername() {
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("Password1!");
//        user.setEmail("test@example.com");
//        userRepository.save(user);
//
//        Optional<User> foundUser = userRepository.findByUsername("testUser");
//
//        assertThat(foundUser).isPresent();
//        assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
//    }
//
//    @Test
//    void shouldReturnEmptyIfUserNotFound() {
//        Optional<User> foundUser = userRepository.findByUsername("nonexistent");
//
//        assertThat(foundUser).isEmpty();
//    }
//}