package pl.jagiela.microservices_ecommerce.product_service.dto.request;

import jakarta.validation.constraints.*;

public record ProductRequestDto(
        @NotBlank(message = "Product name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @Min(value = 0, message = "Price must be positive")
        double price,

        @Min(value = 1, message = "Stock must be higher than 0")
        int stock
) {}