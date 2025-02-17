package pl.jagiela.microservices_ecommerce.product_service.service;

import org.springframework.stereotype.Service;
import pl.jagiela.microservices_ecommerce.product_service.dto.request.ProductRequestDto;
import pl.jagiela.microservices_ecommerce.product_service.dto.response.ProductResponseDto;
import pl.jagiela.microservices_ecommerce.product_service.exception.ProductNotFoundException;
import pl.jagiela.microservices_ecommerce.product_service.model.Product;
import pl.jagiela.microservices_ecommerce.product_service.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public ProductResponseDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        return mapToDto(productRepository.save(product));
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());

        return mapToDto(productRepository.save(product));
    }

    public String deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);

        return "Product delete successfully";
    }

    private ProductResponseDto mapToDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
