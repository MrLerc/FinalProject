package com.example.onlineshop.DataAccessLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    // ---- Derived query ----
    boolean existsByEmailIgnoreCase(String email);

    // ---- Flexible search query with optional filters ----
    @Query("""
        SELECT v
        FROM Vendor v
        WHERE (:name IS NULL OR LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:emailPart IS NULL OR LOWER(v.email) LIKE LOWER(CONCAT('%', :emailPart, '%')))
          AND (:phonePart IS NULL OR (v.phone IS NOT NULL AND LOWER(v.phone) LIKE LOWER(CONCAT('%', :phonePart, '%'))))
          AND (:addressPart IS NULL OR LOWER(v.address) LIKE LOWER(CONCAT('%', :addressPart, '%')))
          AND (:minDateOfBirth IS NULL OR v.dateOfBirth >= :minDateOfBirth)
          AND (:maxDateOfBirth IS NULL OR v.dateOfBirth <= :maxDateOfBirth)
          AND (:minCreated IS NULL OR v.createdAt >= :minCreated)
          AND (:maxCreated IS NULL OR v.createdAt <= :maxCreated)
          AND (:minUpdated IS NULL OR v.updatedAt >= :minUpdated)
          AND (:maxUpdated IS NULL OR v.updatedAt <= :maxUpdated)
    """)
    List<Vendor> searchAll(
            @Param("name") String name,
            @Param("emailPart") String emailPart,
            @Param("phonePart") String phonePart,
            @Param("addressPart") String addressPart,
            @Param("minDateOfBirth") LocalDate minDateOfBirth,
            @Param("maxDateOfBirth") LocalDate maxDateOfBirth,
            @Param("minCreated") Instant minCreated,
            @Param("maxCreated") Instant maxCreated,
            @Param("minUpdated") Instant minUpdated,
            @Param("maxUpdated") Instant maxUpdated
    );
}
