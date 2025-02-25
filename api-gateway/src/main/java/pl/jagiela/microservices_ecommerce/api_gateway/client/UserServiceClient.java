package pl.jagiela.microservices_ecommerce.api_gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/auth/exists/{username}")
    Boolean doesUserExist(@PathVariable String username);
}