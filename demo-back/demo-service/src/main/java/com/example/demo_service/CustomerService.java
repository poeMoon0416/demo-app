package com.example.demo_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(int id) {
        // https://docs.oracle.com/javase/jp/21/docs/api/java.base/java/util/Optional.html
        return customerRepository.findById(id)
                .orElse(Customer.builder()
                        .id(-1)
                        .name("")
                        .sales(List.of())
                        .build());
    }
}
