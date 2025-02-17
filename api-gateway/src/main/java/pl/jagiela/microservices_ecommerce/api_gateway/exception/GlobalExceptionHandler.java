package pl.jagiela.microservices_ecommerce.api_gateway.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Configuration
public class GlobalExceptionHandler {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
                errorAttributes.put("status", HttpStatus.BAD_GATEWAY.value());
                errorAttributes.put("message", "Service unavailable or failed to respond");
                return errorAttributes;
            }
        };
    }
}
