package com.example.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank(message = "Tên loại sản phẩm không được để trống")
        @Column(name = "type_name", nullable = false)
        private String typeName;

        @Column(name = "is_active")
        private Integer isActive = 1;

        // Quan hệ 1 danh mục có nhiều sản phẩm (Tùy chọn)
        @OneToMany(mappedBy = "productType")
        private List<Product> products;
}
