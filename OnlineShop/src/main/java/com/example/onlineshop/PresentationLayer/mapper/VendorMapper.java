package com.example.onlineshop.PresentationLayer.mapper;

import com.example.onlineshop.DataAccessLayer.entity.Vendor;
import com.example.onlineshop.PresentationLayer.dto.vendor.VendorRequest;
import com.example.onlineshop.PresentationLayer.dto.vendor.VendorResponse;

public final class VendorMapper {
    private VendorMapper() {
    }
    public static Vendor toEntity(VendorRequest req){
        return Vendor.builder()
                .name(req.name())
                .email(req.email())
                .phone(req.phone())
                .address(req.address())
                .build();
    }

    public static VendorResponse toResponse(Vendor vendor){
        long count = vendor.getProducts() == null ? 0 : vendor.getProducts().size();
        return new VendorResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getEmail(),
                vendor.getPhone(),
                vendor.getAddress(),
                vendor.getCreatedAt(),
                vendor.getUpdatedAt(),
                count
        );
    }
}
