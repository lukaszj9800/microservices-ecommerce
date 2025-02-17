//package pl.jagiela.microservices_ecommerce.user_service.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.server.ResponseStatusException;
//import pl.jagiela.microservices_ecommerce.user_service.dto.request.UserRegisterRequest;
//import pl.jagiela.microservices_ecommerce.user_service.service.UserService;
//
//class UserControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    void shouldRegisterUserSuccessfully() throws Exception {
//        UserRegisterRequest request = new UserRegisterRequest("testUser", "test@example.com", "Password1!");
//        when(userService.register(any())).thenReturn("User registered successfully");
//
//        mockMvc.perform(post("/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("User registered successfully"));
//    }
//
//    @Test
//    void shouldReturnConflictWhenUserAlreadyExists() throws Exception {
//        UserRegisterRequest request = new UserRegisterRequest("testUser", "test@example.com", "Password1!");
//        when(userService.register(any())).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists"));
//
//        mockMvc.perform(post("/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isConflict());
//    }
//}