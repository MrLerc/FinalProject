package com.example.onlineshop.BusinessLogicLayer;

import com.example.onlineshop.DataAccessLayer.entity.Product;
import com.example.onlineshop.DataAccessLayer.entity.Vendor;
import com.example.onlineshop.DataAccessLayer.repository.ProductRepository;
import com.example.onlineshop.PresentationLayer.dto.product.ProductRequest;
import com.example.onlineshop.PresentationLayer.dto.product.ProductResponse;
import com.example.onlineshop.PresentationLayer.mapper.ProductMapper;
import com.example.onlineshop.utilities.ProductNotFoundException;
import com.example.onlineshop.utilities.DuplicateResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final VendorService vendorService;

    public ProductService(ProductRepository productRepository,
                          VendorService vendorService) {
        this.productRepository = productRepository;
        this.vendorService = vendorService;
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
        if (productRepository.existsByNameIgnoreCaseAndVendorId(req.name(), req.vendorId())) {
            throw new DuplicateResourceException("Product already exists for this vendor: " + req.name());
        }

        Vendor vendor = vendorService.getEntityById(req.vendorId());
        Product entity = ProductMapper.toEntity(req, vendor);
        Product saved = productRepository.save(entity);
        return ProductMapper.toResponse(saved);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        boolean nameChanging = !product.getName().equalsIgnoreCase(req.name());
        if (nameChanging && productRepository.existsByNameIgnoreCaseAndVendorId(req.name(), req.vendorId())) {
            throw new DuplicateResourceException("Product already exists for this vendor: " + req.name());
        }

        Vendor vendor = vendorService.getEntityById(req.vendorId());
        product.setName(req.name());
        product.setDescription(req.description());
        product.setPrice(req.price());
        product.setQuantity(req.quantity());
        product.setVendor(vendor);

        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByVendor(Long vendorId) {
        vendorService.getEntityById(vendorId);
        return productRepository.findByVendorId(vendorId)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> search(
            String name,
            Double minPrice,
            Double maxPrice,
            String description,
            Integer quantity,
            Long vendorId
    ) {
        String nameNorm = normalize(name);
        String descriptionNorm = normalize(description);
        return productRepository.searchAll(
                        nameNorm, minPrice, maxPrice, descriptionNorm, quantity, vendorId
                ).stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    private static String normalize(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }
}