package com.example.onlineshop.DataAccessLayer.repository;

import com.example.onlineshop.DataAccessLayer.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCaseAndVendorId(String name, Long vendorId);

    List<Product> findByVendorId(Long vendorId);

    @Query("""
        SELECT p
        FROM Product p
        WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:description IS NULL OR (p.description IS NOT NULL AND LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))))
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:quantity IS NULL OR p.quantity = :quantity)
          AND (:vendorId IS NULL OR p.vendor.id = :vendorId)
    """)
    List<Product> searchAll(
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("description") String description,
            @Param("quantity") Integer quantity,
            @Param("vendorId") Long vendorId
    );
}