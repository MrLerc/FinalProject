package com.example.onlineshop.PresentationLayer.mapper;

import com.example.onlineshop.DataAccessLayer.entity.Product;
import com.example.onlineshop.DataAccessLayer.entity.Vendor;
import com.example.onlineshop.PresentationLayer.dto.product.ProductRequest;
import com.example.onlineshop.PresentationLayer.dto.product.ProductResponse;

public final class ProductMapper {
    private ProductMapper() {}

    public static Product toEntity(ProductRequest req, Vendor vendor){
        return Product.builder()
                .name(req.name())
                .price(req.price())
                .description(req.description())
                .quantity(req.quantity())
                .vendor(vendor)
                .build();
    }

    public static ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getQuantity(),
                product.getVendor().getId(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}