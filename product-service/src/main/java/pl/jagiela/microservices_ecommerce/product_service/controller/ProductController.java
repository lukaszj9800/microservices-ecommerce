package pl.jagiela.microservices_ecommerce.product_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.jagiela.microservices_ecommerce.product_service.dto.request.ProductRequestDto;
import pl.jagiela.microservices_ecommerce.product_service.dto.response.ProductResponseDto;
import pl.jagiela.microservices_ecommerce.product_service.service.ProductService;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Product Controller", description = "Product-related operations")
@Validated
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "Product list downloaded")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get a product by ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product with the given id has been downloaded"),
            @ApiResponse(responseCode = "404", description = "Product with the given id not found")
    })
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Add a new product")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Product field validation failed")
    })
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @Operation(summary = "Update a product by ID")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product with the given id not found"),
            @ApiResponse(responseCode = "400", description = "Product field validation failed")
    })
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @Operation(summary = "Delete a product by ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product with the given id not found"),
    })
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}