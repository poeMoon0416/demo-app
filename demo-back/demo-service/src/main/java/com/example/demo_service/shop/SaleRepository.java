package com.example.demo_service.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    // デフォルトのOptional<Sale>でないと存在しないときnullでバグる
    // public Sale findById(int id);
}
