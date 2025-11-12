package com.example.onlineshop.PresentationLayer.controller;

import com.example.onlineshop.BusinessLogicLayer.ProductService;
import com.example.onlineshop.PresentationLayer.dto.product.ProductRequest;
import com.example.onlineshop.PresentationLayer.dto.product.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responseBody = productService.getAll();
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse responseBody = productService.getById(id);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest requestBody) {
        ProductResponse responseBody = productService.create(requestBody);
        return ResponseEntity.created(URI.create("/products/" + responseBody.id()))
                .body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest requestBody) {
        ProductResponse responseBody = productService.update(id, requestBody);
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok("Product " + id + " deleted.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String descriptionContains,
            @RequestParam(required = false) Instant minCreated,
            @RequestParam(required = false) Instant maxCreated,
            @RequestParam(required = false) Instant minUpdated,
            @RequestParam(required = false) Instant maxUpdated
    ) {
        List<ProductResponse> responseBody = productService.search(
                name, descriptionContains,
                minCreated, maxCreated,
                minUpdated, maxUpdated
        );
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }
}