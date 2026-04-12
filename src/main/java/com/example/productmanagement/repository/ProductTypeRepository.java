package com.example.productmanagement.repository;

import com.example.productmanagement.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    List<ProductType> findAllByIsActive(Integer isActive);
}
