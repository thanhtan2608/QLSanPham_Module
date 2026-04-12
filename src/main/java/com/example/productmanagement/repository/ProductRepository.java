package com.example.productmanagement.repository;

import com.example.productmanagement.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
   List<Product> findByIsDeleted(Integer isDeleted, Sort sort);
   Optional<Product> findByProductCode(String productCode);
   @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 " +
           "AND (?1 IS NULL OR p.productType.id = ?1) " +
           "AND (p.name LIKE %?2% OR p.productCode LIKE %?2%)")
   List<Product> searchActiveProducts(Long typeId,String keyword, Sort sort);
}
