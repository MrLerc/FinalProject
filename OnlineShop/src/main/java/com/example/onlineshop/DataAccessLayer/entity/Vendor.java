package com.example.onlineshop.DataAccessLayer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "vendors",
        uniqueConstraints = { @UniqueConstraint(name = "uk_vendor_email", columnNames = "email"), @UniqueConstraint(name = "uk_vendor_address", columnNames = "address") }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 25)
    private String phone;

    @Column( nullable = false, length = 255)
    private String address;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;


    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
