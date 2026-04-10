package com.example.productmanagement.controller;

import com.example.productmanagement.entity.ProductType;
import com.example.productmanagement.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductTypeController {
    @Autowired
    private ProductTypeRepository typeRepo;

    @GetMapping("/product-types")
    public String listProductTypes(Model model) {
        // Lấy danh sách ProductType từ DB
        model.addAttribute("listProductTypes", typeRepo.findAll());
        return "product-types"; // Trỏ đến file html mới
    }

    // Hiển thị form thêm mới
    @GetMapping("/product-types/new")
    public String showTypeForm(Model model) {
        // QUAN TRỌNG: Tên "productType" phải khớp với th:object trong HTML
        model.addAttribute("productType", new ProductType());
        return "type-form"; // Đảm bảo tên file HTML là type-form.html
    }

    // Lưu loại sản phẩm
    @PostMapping("/product-types/save")
    public String saveType(ProductType productType) {
        typeRepo.save(productType);
        return "redirect:/product-types";
    }
}
