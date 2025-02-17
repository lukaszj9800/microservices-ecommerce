package pl.jagiela.microservices_ecommerce.product_service.dto.response;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        double price,
        int stock
) {}