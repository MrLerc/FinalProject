package com.example.onlineshop.BusinessLogicLayer;

import com.example.onlineshop.DataAccessLayer.entity.Product;
import com.example.onlineshop.DataAccessLayer.repository.ProductRepository;
import com.example.onlineshop.DataAccessLayer.entity.Vendor;
import com.example.onlineshop.PresentationLayer.dto.product.ProductRequest;
import com.example.onlineshop.PresentationLayer.mapper.ProductMapper;
import com.example.onlineshop.utilities.ProductNotFoundException;
import com.example.onlineshop.DataAccessLayer.repository.VendorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;

    public ProductService(ProductRepository productRepository, VendorRepository vendorRepository) {
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest req) {
        Vendor vendor = vendorRepository.findById(req.vendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found: " + req.vendorId()));

        Product product = ProductMapper.toEntity(req, vendor);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest req) {
        Product current = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (req.productName() != null) current.setProductName(req.productName());
        if (req.price() != null) current.setPrice(req.price());
        if (req.description() != null) current.setDescription(req.description());
        if (req.quantity() != null) current.setQuantity(req.quantity());
        if (req.vendorId() != null) {
            Vendor vendor = vendorRepository.findById(req.vendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found: " + req.vendorId()));
            current.setVendor(vendor);
        }

        Product saved = productRepository.save(current);
        return ProductMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

}