package com.example.onlineshop;

import com.example.onlineshop.DataAccessLayer.entity.Product;
import com.example.onlineshop.DataAccessLayer.entity.Vendor;
import com.example.onlineshop.DataAccessLayer.repository.ProductRepository;
import com.example.onlineshop.DataAccessLayer.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnlineShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApplication.class, args);
    }
    @Bean
    CommandLineRunner seed(VendorRepository vendors, ProductRepository products) {
        return args -> {

            if (vendors.count() == 0 && products.count() == 0) {
                Vendor v1 = vendors.save(
                        Vendor.builder()
                                .name("GreenLeaf Supplies")
                                .email("greenleaf@example.com")
                                .address("123 Maple Street, Toronto")
                                .phone("416-555-1122")
                                .build()
                );

                Vendor v2 = vendors.save(
                        Vendor.builder()
                                .name("TechNova Solutions")
                                .email("technova@example.ca")
                                .address("456 Queen St, Montreal")
                                .phone("514-555-8899")
                                .build()
                );

                Vendor v3 = vendors.save(
                        Vendor.builder()
                                .name("BlueWave Electronics")
                                .email("bluewave@example.com")
                                .address("789 King Ave, Vancouver")
                                .phone("604-555-7711")
                                .build()
                );
                // Assign 2 products to v1 (GreenLeaf Supplies)
                Product p1 = products.save(
                        Product.builder()
                                .name("Organic Fertilizer")
                                .description("High-quality organic fertilizer for home gardens")
                                .price(25.50)
                                .quantity(100)
                                .vendor(v1)
                                .build()
                );

                Product p2 = products.save(
                        Product.builder()
                                .name("Garden Tools Set")
                                .description("Durable 5-piece gardening tools set")
                                .price(45.00)
                                .quantity(50)
                                .vendor(v1)
                                .build()
                );

                Product p3 = products.save(
                        Product.builder()
                                .name("Wireless Mouse")
                                .description("Ergonomic wireless mouse with USB receiver")
                                .price(29.99)
                                .quantity(200)
                                .vendor(v2)
                                .build()
                );

                Product p4 = products.save(
                        Product.builder()
                                .name("LED Monitor")
                                .description("27-inch 4K UHD LED monitor")
                                .price(299.99)
                                .quantity(30)
                                .vendor(v3)
                                .build()
                );

                Product p5 = products.save(
                        Product.builder()
                                .name("Mechanical Keyboard")
                                .description("RGB backlit mechanical keyboard")
                                .price(129.99)
                                .quantity(40)
                                .vendor(v3)
                                .build()
                );

            }
        };
    }
}