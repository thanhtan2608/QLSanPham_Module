package com.example.productmanagement.repository;

import com.example.productmanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
   List<Product> findByIsDeleted(Integer isDeleted);
   Optional<Product> findByProductCode(String productCode);
}
