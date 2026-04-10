package com.example.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    @Column(name = "product_code")
    private String productCode;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotNull(message = "Giá không được để trống")
    @Min( value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    private String description;



    @Column(name = "is_deleted")
    private Integer isDeleted = 0; // Mặc định là 0 (chưa xóa)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Tự động gán ngày giờ khi tạo mới hoặc cập nhật
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    @NotNull(message = "Vui lòng chọn loại sản phẩm")
    @ManyToOne
    @JoinColumn(name = "type_id") // Tên cột khóa ngoại trong bảng products
    private ProductType productType;
}
