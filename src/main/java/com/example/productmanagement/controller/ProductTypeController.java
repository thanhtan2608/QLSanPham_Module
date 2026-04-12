package com.example.productmanagement.controller;

import com.example.productmanagement.entity.ProductType;
import com.example.productmanagement.repository.ProductTypeRepository;
import com.example.productmanagement.service.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductTypeController {
    @Autowired
    private ProductTypeRepository typeRepo;
    @Autowired
    private ProductTypeService typeService;

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
    public String saveType(@Valid @ModelAttribute("productType") ProductType productType,
                           BindingResult result,
                           Model model,
                           RedirectAttributes ra) {

        // 1. Kiểm tra nếu có lỗi validation từ các Annotation trong Entity
        if (result.hasErrors()) {
            // Trả về lại form thêm mới nếu có lỗi
            return "type-form";
        }

        // 2. Lưu vào database nếu dữ liệu hợp lệ
        typeService.saveType(productType);

        // 3. Thông báo thành công
        ra.addFlashAttribute("message", "Lưu loại sản phẩm thành công!");
        return "redirect:/product-types";
    }
    @GetMapping("/product-types/delete/{id}")
    public String deleteType(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            typeService.deleteType(id);
            ra.addFlashAttribute("message", "Đã xóa loại sản phẩm thành công!");
        } catch (Exception e) {
            // Trường hợp lỗi (ví dụ: đang có sản phẩm thuộc loại này nên không cho xóa)
            ra.addFlashAttribute("error", "Không thể xóa loại sản phẩm này vì đang có sản phẩm thuộc danh mục!");
        }
        return "redirect:/product-types";
    }
    @GetMapping("/product-types/edit/{id}")
    public String showEditTypeForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            ProductType productType = typeService.getById(id);
            model.addAttribute("productType", productType);
            model.addAttribute("pageTitle", "Chỉnh sửa loại sản phẩm (ID: " + id + ")");
            return "type-form";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/product-types";
        }
    }
}
