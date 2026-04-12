package com.example.productmanagement.service;

import com.example.productmanagement.entity.ProductType;
import com.example.productmanagement.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository repo;

    // Lấy tất cả loại sản phẩm
    public List<ProductType> getAllTypes() {
        return repo.findAll();
    }

    // Lưu hoặc cập nhật loại sản phẩm
    public void saveType(ProductType type) {
        repo.save(type);
    }

    // Tìm kiếm loại sản phẩm theo ID
    public ProductType getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy loại sản phẩm với ID: " + id));
    }

    // Xóa loại sản phẩm (Xóa hẳn hoặc bạn có thể làm xóa mềm tương tự Product)
    public void deleteType(Long id) {
        // Kiểm tra xem ID có tồn tại không trước khi xóa
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    // Kiểm tra tồn tại theo ID
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
    public List<ProductType> getActiveTypes() {
        return repo.findAllByIsActive(1);
    }

}
