package com.example.demo_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    public Sale findById(int id) {
        return saleRepository.findById(id).orElse(
                new Sale(-1, -1, new Customer()));
    }
}
