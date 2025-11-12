package com.example.onlineshop.PresentationLayer.controller;

import com.example.onlineshop.BusinessLogicLayer.VendorService;
import com.example.onlineshop.BusinessLogicLayer.ProductService;
import com.example.onlineshop.DataAccessLayer.entity.Product;
import com.example.onlineshop.PresentationLayer.dto.product.ProductResponse;
import com.example.onlineshop.PresentationLayer.dto.vendor.VendorRequest;
import com.example.onlineshop.PresentationLayer.dto.vendor.VendorResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendors")
@Validated
public class VendorController {
    private final VendorService vendorService;
    private final ProductService productService;
    public VendorController(VendorService vendorService, ProductService productService){
        this.vendorService = vendorService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<VendorResponse>> getAllVendors() {
        List<VendorResponse> responseBody = vendorService.getAll();
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VendorResponse> getVendorById(@PathVariable Long id) {
        VendorResponse responseBody = vendorService.getById(id);
        return ResponseEntity.ok(responseBody);
    }
    @PostMapping
    public ResponseEntity<VendorResponse> createVendor(@Valid @RequestBody VendorRequest requestBody) {
        VendorResponse responseBody = vendorService.create(requestBody);
        return ResponseEntity.created(URI.create("/vendors/" + responseBody.id()))
                .body(responseBody);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable Long id, @Valid @RequestBody VendorRequest requestBody) {
        VendorResponse responseBody = vendorService.update(id, requestBody);
        return ResponseEntity.ok(responseBody);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.ok("Owner " + id + " deleted.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<VendorResponse>> searchVendors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String emailContains,
            @RequestParam(required = false) String phoneContains,
            @RequestParam(required = false) String addressContains,
            @RequestParam(required = false) Instant minCreated,
            @RequestParam(required = false) Instant maxCreated,
            @RequestParam(required = false) Instant minUpdated,
            @RequestParam(required = false) Instant maxUpdated
    ) {
        List<VendorResponse> responseBody = vendorService.search(
                name, emailContains, phoneContains, addressContains,
                minCreated, maxCreated, minUpdated, maxUpdated
        );
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByOwner(@PathVariable Long id) {
        List<ProductResponse> responseBody = productService.getProductsByVendor(id);
        if (responseBody.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseBody);
    }
}
