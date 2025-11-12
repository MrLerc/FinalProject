package com.example.onlineshop.PresentationLayer.dto.vendor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record VendorRequest(
        @NotBlank @Size(max= 80) String name,
        @NotBlank @Past
        LocalDate dateOfBirth,
        @NotBlank @Email @Size(max= 255) String email,
        @Size(max= 25) String phone,
        @NotBlank @Size(max= 255) String address
){

}



