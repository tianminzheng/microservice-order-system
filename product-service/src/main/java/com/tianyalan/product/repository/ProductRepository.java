package com.tianyalan.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianyalan.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByProductCode(String productCode);
}
