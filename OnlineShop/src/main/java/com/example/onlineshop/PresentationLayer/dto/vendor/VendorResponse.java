package com.example.onlineshop.PresentationLayer.dto.vendor;

import java.time.Instant;
import java.time.LocalDate;

public record VendorResponse(
        Long id,
        String name,
        String email,
        String phone,
        String address,
        Instant createdAt,
        Instant updatedAt,
        long productCount
) {
}
