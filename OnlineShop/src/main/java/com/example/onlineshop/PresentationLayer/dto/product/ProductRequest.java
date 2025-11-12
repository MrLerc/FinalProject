package com.example.onlineshop.PresentationLayer.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank @Size(max = 100) String name,
        @NotNull @Positive Double price,
        @Size(max = 255) String description,
        @NotNull @Positive Integer quantity,
        @NotNull Long vendorId
) {

}