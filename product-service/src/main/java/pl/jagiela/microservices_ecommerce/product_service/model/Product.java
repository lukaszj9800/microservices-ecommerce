package pl.jagiela.microservices_ecommerce.product_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 0, message = "Price must be positive")
    private double price;

    @Min(value = 0, message = "Stock must be positive")
    private int stock;
}