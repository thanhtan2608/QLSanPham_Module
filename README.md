# Module Quản lý sản phẩm
 Đây là một module quản lý sản phẩm được xây dựng bằng Java Spring Boot, sử dụng kiến trúc MVC (Model-View-Controller)
 
 🚀 Tính năng chính
 
. Quản lý Sản phẩm: Thêm, sửa, xóa (xóa mềm), tìm kiếm, bộ lọc sắp xếp dữ liệu và hiển thị danh sách sản phẩm.

. Quản lý Hình ảnh: Upload ảnh sản phẩm trực tiếp lên server.

. Validation: Kiểm tra dữ liệu đầu vào chặt chẽ.

. Khôi phục dữ liệu: Cơ chế kiểm tra mã sản phẩm cũ để khôi phục dữ liệu đã xóa mềm thay vì tạo mới trùng lặp.

. Quản lý Loại sản phẩm

🛠 Công nghệ sử dụng

.Backend: Java 17+, Spring Boot 3.x, Spring Data JPA, Spring Validation.

.Frontend: Thymeleaf, HTML5, CSS3, JavaScript.

.Database: MySQL (Quản lý qua phpMyAdmin).

.Công cụ: IntelliJ IDEA, Maven, Git.

⚙️ Cấu hình hệ thống

1. Cơ sở dữ liệu

   <img width="853" height="510" alt="image" src="https://github.com/user-attachments/assets/e750b0e9-551c-4ef1-ab47-d482fdb11ef8" />


File database nằm trong src/main/java/com/example/productmanegement/database/sp.sql

Import file database trên phpmyadmin có tên product_db và cấu hình trong file src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/product_db

spring.datasource.username=root

spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

2. Hướng dẫn cài đặt

Clone dự án:
git clone https://github.com/thanhtan2608/QLSanPham_Module.git

Bật WampServer

Mở dự án: Mở bằng IntelliJ IDEA và đợi Maven tải các dependencies.

Chạy ứng dụng: Tìm file  ProductManegementApplication.java và nhấn Run.

Truy cập: Mở trình duyệt và nhập http://localhost:8080/products.

Developer: Thanh Tấn
