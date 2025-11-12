package com.example.onlineshop.DataAccessLayer.repository;

import com.example.onlineshop.DataAccessLayer.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCaseAndVendorId(String name, Long vendorId);

    @Query("""
        SELECT p
        FROM Product p
        WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:descriptionPart IS NULL OR (p.description IS NOT NULL AND LOWER(p.description) LIKE LOWER(CONCAT('%', :descriptionPart, '%'))))
          AND (:minCreated IS NULL OR p.createdAt >= :minCreated)
          AND (:maxCreated IS NULL OR p.createdAt <= :maxCreated)
          AND (:minUpdated IS NULL OR p.updatedAt >= :minUpdated)
          AND (:maxUpdated IS NULL OR p.updatedAt <= :maxUpdated)
    """)
    List<Product> searchAll(
            @Param("name") String name,
            @Param("descriptionPart") String descriptionPart,
            @Param("minCreated") Instant minCreated,
            @Param("maxCreated") Instant maxCreated,
            @Param("minUpdated") Instant minUpdated,
            @Param("maxUpdated") Instant maxUpdated
    );
}