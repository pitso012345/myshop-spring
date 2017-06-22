package com.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
