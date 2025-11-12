ONLINE SHOP BACKEND

Project Description
-------------------
This project is a RESTful web service for an Online Shop system, developed using Spring Boot and an H2 in-memory database. 
It allows management of Vendors and Products with CRUD operations, search functionality, and aggregated endpoints showing 
the relationships between vendors and their products.

Key Features:
- Create, read, update, and delete Vendors and Products
- Search Vendors and Products with multiple filters
- View all products of a specific Vendor
- Data seeding on startup (preloaded with sample Vendors and Products)
- RESTful design with proper HTTP status codes
- Input validation with @Valid annotations
- Custom exceptions and centralized error handling

Project Structure
-----------------
com.example.onlineshop
|
├─ OnlineShopApplication.java        Main Spring Boot class
├─ bootstrap/
|  └─ DataSeeder.java                Seed data into H2 database
├─ DataAccessLayer/
|  ├─ entity/
|  |  ├─ Vendor.java
|  |  └─ Product.java
|  └─ repository/
|     ├─ VendorRepository.java
|     └─ ProductRepository.java
├─ BusinessLogicLayer/
|  ├─ VendorService.java
|  └─ ProductService.java
├─ PresentationLayer/
|  ├─ controller/
|  |  ├─ VendorController.java
|  |  └─ ProductController.java
|  └─ dto/
|     ├─ vendor/
|     |  ├─ VendorRequest.java
|     |  └─ VendorResponse.java
|     └─ product/
|        ├─ ProductRequest.java
|        └─ ProductResponse.java
├─ mapper/
|  ├─ VendorMapper.java
|  └─ ProductMapper.java
└─ utilities/
   ├─ DuplicateResourceException.java
   ├─ VendorNotFoundException.java
   ├─ ProductNotFoundException.java
   └─ GlobalExceptionHandler.java

How to Run Locally
------------------
1. Clone the repository:
   git clone <your-repo-url>
   cd onlineshop

2. Run the application with Maven:
   ./mvnw spring-boot:run

3. Access the H2 console:
   http://localhost:8080/h2-console
   JDBC URL: jdbc:h2:mem:demo
   User Name: sa
   Password: <leave blank>

4. Backend URL:
   http://localhost:8080

API Endpoints
-------------

VENDORS
GET     /vendors                - Get all vendors
GET     /vendors/{id}           - Get vendor by ID
POST    /vendors                - Create a new vendor
PUT     /vendors/{id}           - Update vendor by ID
DELETE  /vendors/{id}           - Delete vendor by ID
GET     /vendors/search         - Search vendors by filters
GET     /vendors/{id}/products  - Get all products for a specific vendor

PRODUCTS
GET     /products               - Get all products
GET     /products/{id}          - Get product by ID
POST    /products               - Create a new product
PUT     /products/{id}          - Update product by ID
DELETE  /products/{id}          - Delete product by ID
GET     /products/search        - Search products by filters
GET     /products/{id}/vendor   - Get vendor info for a product

H2 Database Configuration
--------------------------
spring.datasource.url=jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

Notes
-----
- The application preloads at least 2 vendors and 3 products for testing. You can expand the dataset in DataSeeder.java.
- All DTOs are used for request/response separation.
- Validation annotations ensure correct data (e.g., @NotBlank, @Email, @Positive).
- Global exception handling provides meaningful HTTP 400/404 responses.

Deployment
----------
Currently runs locally on http://localhost:8080
