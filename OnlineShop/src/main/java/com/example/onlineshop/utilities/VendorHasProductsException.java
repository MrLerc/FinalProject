package com.example.onlineshop.utilities;

public class VendorHasProductsException extends RuntimeException {
    public VendorHasProductsException(Long vendorId) {
        super("Vendor " + vendorId + " cannot be deleted because they still own Products.");
    }
}
