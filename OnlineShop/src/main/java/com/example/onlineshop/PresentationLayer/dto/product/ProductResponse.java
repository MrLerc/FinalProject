package com.example.onlineshop.PresentationLayer.dto.product;

import java.time.Instant;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        String description,
        Integer quantity,
        Long vendorId,
        Instant createdAt,
        Instant updatedAt
) {

}
