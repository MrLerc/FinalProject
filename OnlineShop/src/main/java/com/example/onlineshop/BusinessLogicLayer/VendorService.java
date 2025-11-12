package com.example.onlineshop.BusinessLogicLayer;

import com.example.onlineshop.DataAccessLayer.Vendor;
import com.example.onlineshop.DataAccessLayer.VendorRepository;
import com.example.onlineshop.PresentationLayer.dto.vendor.VendorRequest;
import com.example.onlineshop.PresentationLayer.dto.vendor.VendorResponse;
import com.example.onlineshop.PresentationLayer.mapper.VendorMapper;
import com.example.onlineshop.utilities.DuplicateResourceException;
import com.example.onlineshop.utilities.VendorHasProductsException;
import com.example.onlineshop.utilities.VendorNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class VendorService {
    private final VendorRepository vendorRepository;
    public VendorService(VendorRepository repository) {
        this.vendorRepository = repository;
    }

    @Transactional(readOnly = true)
    public List<VendorResponse> getAll() {
        return vendorRepository.findAll()
                .stream().map(VendorMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public VendorResponse getById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));
        return VendorMapper.toResponse(vendor);
    }

    @Transactional
    public VendorResponse create(VendorRequest req) {
        if (vendorRepository.existsByEmailIgnoreCase(req.email())) {
            throw new DuplicateResourceException("Vendor email already exists: " + req.email());
        }
        if (vendorRepository.existsByAddressIgnoreCase(req.address())) {
            throw new DuplicateResourceException("Vendor address already exists: " + req.address());
        }
        Vendor entity = VendorMapper.toEntity(req);
        Vendor saved = vendorRepository.save(entity);
        return VendorMapper.toResponse(saved);
    }

    @Transactional
    public VendorResponse update(Long id, VendorRequest req) {
        Vendor current = vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));
        boolean emailChanging = req.email() != null
                && !req.email().equalsIgnoreCase(current.getEmail());
        if (emailChanging && vendorRepository.existsByEmailIgnoreCase(req.email())) {
            throw new DuplicateResourceException("Vendor email already exists: " + req.email());
        }
        boolean addressChanging = req.address() != null
                && !req.address().equals(current.getAddress());
        if (addressChanging && vendorRepository.existsByAddressIgnoreCase(req.address())) {
            throw new DuplicateResourceException("Vendor address already exists: " + req.address());
        }
        current.setName(req.name());
        current.setDateOfBirth(req.dateOfBirth());
        current.setEmail(req.email());
        current.setPhone(req.phone());
        current.setAddress(req.address());
        Vendor saved = vendorRepository.save(current);
        return VendorMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Vendor owner = vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));
        if (owner.getProducts() != null && !owner.getProducts().isEmpty()) {
            throw new VendorHasProductsException(id);
        }
        vendorRepository.delete(owner);
    }

    @Transactional(readOnly = true)
    public List<VendorResponse> search(
            String name,
            LocalDate dateOfBirth,
            String emailContains,
            String phoneContains,
            String addressContains,
            Instant minCreated,
            Instant maxCreated,
            Instant minUpdated,
            Instant maxUpdated
    ) {
        String nameNorm = normalize(name);
        String emailNorm = normalize(emailContains);
        String phoneNorm = normalize(phoneContains);
        String addressNorm = normalize(addressContains);
        return vendorRepository.searchAll(
                        nameNorm, emailNorm, phoneNorm,  addressNorm, dateOfBirth, dateOfBirth,
                        minCreated, maxCreated, minUpdated, maxUpdated
                )
                .stream().map(VendorMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Vendor getEntityById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));
    }

    private static String normalize(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }
}

