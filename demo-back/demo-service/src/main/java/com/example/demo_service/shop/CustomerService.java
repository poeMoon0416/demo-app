package com.example.demo_service.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html
// RuntimeExceptionかErrorでロールバックされるようだ
@Transactional
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

    public Customer createCustomer(Customer customer) {
        // NOT NULLなのにnullを保存しようとするとエラー
        return customerRepository.saveAndFlush(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return customerRepository.existsById(id == null ? -1 : id);
    }

    public List<Customer> findByName(String name) {
        // 自動であいまい検索にはならないので注意
        if (!name.startsWith("%")) {
            name = "%" + name;
        }
        if (!name.endsWith("%")) {
            name = name + "%";
        }
        System.out.println("findByName's name: " + name);
        return customerRepository.findByNameLikeOrderByNameDesc(name);
    }
}
