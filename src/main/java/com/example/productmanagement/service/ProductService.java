package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> getAll(Long typeId,String keyword,String field, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        if (keyword != null && !keyword.isEmpty()|| typeId != null) {
            return repo.searchActiveProducts(typeId,keyword, sort);
        }

        // 2. Gọi Repository lọc sản phẩm chưa xóa (0) và truyền sort vào
        return repo.findByIsDeleted(0, sort);
    }

    public void save(Product p) {
        if (p.getId() != null) {
            Product existingProduct = repo.findById(p.getId()).orElse(null);
            if (existingProduct != null) {
                p.setCreatedAt(existingProduct.getCreatedAt());
            }
        }
        repo.save(p);
    }

    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        // 1. Tìm sản phẩm trong DB
        Product product = repo.findById(id).orElse(null);

        if (product != null) {
            // 2. Chuyển trạng thái xóa thành 1 (Đã xóa)
            product.setIsDeleted(1);

            // 3. Lưu lại vào DB (JPA sẽ thực hiện lệnh UPDATE)
            repo.save(product);
        }
    }
    public Optional<Product> findByProductCode(String productCode) {
        // Gọi sang Repository để lấy dữ liệu
        return repo.findByProductCode(productCode);
    }


    }

