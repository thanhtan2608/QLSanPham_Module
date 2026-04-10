package com.example.productmanagement.controller;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.repository.ProductTypeRepository;
import com.example.productmanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductTypeRepository typeRepo;
    @Autowired
    private ProductTypeRepository productTypeRepo;

    @GetMapping("/products")
    public String viewHomePage(Model model) {
        model.addAttribute("listProducts", service.getAll());
        model.addAttribute("listTypes", typeRepo.findAll());
        return "index"; // Sẽ tìm file index.html trong thư mục templates
    }
    // 1. Hiển thị Form tạo mới
    @GetMapping("/products/new")
    public String showCreateForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        // Lấy danh sách loại sản phẩm để hiện trong ô chọn (select)
        model.addAttribute("listTypes", productTypeRepo.findAll());

        return "product-form"; // Tên file HTML tạo ở bước 3
    }

    // 2. Xử lý lưu dữ liệu
    @PostMapping("/products/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              @RequestParam("fileImage") MultipartFile multipartFile, // Thêm dòng này
                              Model model,
                              RedirectAttributes ra) throws IOException {

        // 1. Kiểm tra lỗi validation (tên, giá, loại...)
        if (result.hasErrors()) {
            model.addAttribute("listTypes", productTypeRepo.findAll());
            return "product-form";
        }
        // --- LOGIC KIỂM TRA TỒN TẠI VÀ KHÔI PHỤC ---
        // Chỉ kiểm tra khi tạo mới (id == null)
        if (product.getId() == null) {
            Optional<Product> existingProduct = service.findByProductCode(product.getProductCode());

            if (existingProduct.isPresent()) {
                Product oldData = existingProduct.get();

                if (oldData.getIsDeleted() == 1) {
                    // Trường hợp đã bị xóa mềm: Khôi phục lại
                    oldData.setIsDeleted(0);
                    // Cập nhật lại các thông tin mới từ form nếu muốn
                    oldData.setName(product.getName());
                    oldData.setPrice(product.getPrice());
                    oldData.setProductType(product.getProductType());
                    oldData.setDescription(product.getDescription());

                    if (multipartFile.isEmpty()) {
                        // Nếu không upload ảnh mới, lấy lại tên file ảnh cũ từ DB
                        product.setImageUrl(oldData.getImageUrl());
                    } else {
                        // Nếu có upload ảnh mới, xử lý lưu file mới (đoạn code upload bên dưới)
                        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
                        product.setImageUrl(fileName);
                        // Thực hiện copy file vào thư mục uploads (như code cũ)
                    }

                    service.save(oldData);
                    ra.addFlashAttribute("message", "Sản phẩm từng tồn tại đã được khôi phục thành công!");
                    return "redirect:/products";
                } else {
                    // Trường hợp đang tồn tại bình thường: Báo lỗi trùng mã
                    result.rejectValue("productCode", "error.product", "Mã sản phẩm này đã tồn tại trên hệ thống!");
                    model.addAttribute("listTypes", productTypeRepo.findAll());
                    return "product-form";
                }
            }
        }
        // 2. Xử lý Upload Ảnh
        if (!multipartFile.isEmpty()) {
            // Lấy tên file gốc
            String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());

            // Gán tên file vào thuộc tính imageUrl của đối tượng product để lưu vào DB
            product.setImageUrl(fileName);

            // Xác định thư mục lưu trữ (ví dụ: thư mục "uploads" ở gốc dự án)
            String uploadDir = "uploads/";
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);

            // Tạo thư mục nếu chưa tồn tại
            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            // Copy file vào thư mục server
            try (java.io.InputStream inputStream = multipartFile.getInputStream()) {
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                java.nio.file.Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        } else {
            // Nếu không upload ảnh mới mà là đang sửa sản phẩm,
            // bạn nên giữ lại link ảnh cũ (tùy vào logic của bạn)
            if (product.getId() != null) {
                Product oldProduct = service.getById(product.getId());
                product.setImageUrl(oldProduct.getImageUrl());
            }
        }

        // 3. Lưu vào Database
        service.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = service.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("listTypes", productTypeRepo.findAll());
        return "product-edit"; // Tên file HTML mới của bạn
    }
    // 2. Xử lý cập nhật (Dùng mapping riêng /update)
    @PostMapping("/products/update")
    public String updateProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult result,
                                @RequestParam("fileImage") MultipartFile multipartFile,
                                Model model
                                ) throws IOException{
        if (result.hasErrors()) {
            model.addAttribute("listTypes", productTypeRepo.findAll());
            return "product-edit";
        }

        // Logic: Trước khi save, lấy lại ngày tạo cũ từ DB để không bị mất (null)
        Product oldProduct = service.getById(product.getId());
        if (!multipartFile.isEmpty()) {
            // TRƯỜNG HỢP: Người dùng chọn ảnh mới
            String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
            product.setImageUrl(fileName);

            // Code upload file (giống như phần Save đã làm)
            String uploadDir = "uploads/";
            // ... (copy file vào folder uploads) ...
        } else {
            // TRƯỜNG HỢP: Người dùng không chọn ảnh mới -> GIỮ LẠI ẢNH CŨ
            product.setImageUrl(oldProduct.getImageUrl());
        }
        product.setCreatedAt(oldProduct.getCreatedAt());

        service.save(product);
        return "redirect:/products";
    }
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        // Gọi hàm xóa trong service
        service.deleteProduct(id);

        // Xóa xong quay lại trang danh sách
        return "redirect:/products";
    }
}
