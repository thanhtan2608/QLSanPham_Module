-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3306
-- Thời gian đã tạo: Th4 10, 2026 lúc 01:43 PM
-- Phiên bản máy phục vụ: 5.7.31
-- Phiên bản PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `sp`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_code` varchar(255) DEFAULT NULL,
  `type_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT 'Tên sản phẩm',
  `price` decimal(38,2) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
  `is_deleted` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_code` (`product_code`),
  KEY `idx_product_name` (`name`),
  KEY `idx_product_type_id` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `product_code`, `type_id`, `name`, `price`, `image_url`, `description`, `created_at`, `updated_at`, `is_deleted`) VALUES
(1, '1111', 1, 'aaaaaaaaa', '4.20', 'hinh.jpg', '', '2026-04-08 13:38:51', '2026-04-10 08:41:15', 1),
(2, '4', 1, 'aaaa', '45.00', NULL, NULL, '2026-04-09 06:45:35', '2026-04-09 07:54:27', 1),
(3, '1524', 1, 'aaaaaaaaa', '45000.00', NULL, '', '2026-04-09 07:58:48', '2026-04-10 07:48:32', 1),
(4, '001', 1, 'Mi', '40000.00', NULL, '', '2026-04-10 07:21:27', '2026-04-10 07:48:34', 1),
(5, '0002', 1, 'bbb', '4000.00', 'hinh.jpg', '', '2026-04-10 07:37:29', '2026-04-10 07:37:29', 0),
(6, '003', 1, 'nuoc', '4000.00', 'hinh.jpg', '', '2026-04-10 07:42:48', '2026-04-10 07:42:48', 0),
(7, '0003', 1, 'aaaaaaaaa', '4.00', 'hinh.jpg', '', '2026-04-10 07:48:21', '2026-04-10 07:48:21', 0),
(9, '0005', 1, 'a', '4.30', 'hinh.jpg', '', '2026-04-10 08:27:27', '2026-04-10 08:41:23', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_types`
--

DROP TABLE IF EXISTS `product_types`;
CREATE TABLE IF NOT EXISTS `product_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL COMMENT 'Tên loại',
  `is_active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `product_types`
--

INSERT INTO `product_types` (`id`, `type_name`, `is_active`) VALUES
(1, 'aaa', 1),
(2, 'bb', 0),
(3, 'VIp', 1);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `product_types` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
