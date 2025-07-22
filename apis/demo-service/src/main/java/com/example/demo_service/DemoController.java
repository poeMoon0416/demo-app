package com.example.demo_service;

import org.springframework.web.bind.annotation.RestController;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class DemoController {

    @Autowired
    DemoRepository demoRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SaleRepository saleRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public List<DemoDto> findAll() {
        // DemoDto dd = new DemoDto(3, "abc");
        // new DemoDto();
        // System.out.println(dd);
        // dd.setStr("def");
        // System.out.println(dd.getStr());
        // return demoRepository.findAll();
        // List<String> strs = new ArrayList<>(List.of("a", "b", "c"));
        // return strs;
        // return new ArrayList<DemoDto>(List.of(new DemoDto(1, "abc"), new DemoDto(2,
        // "def"), new DemoDto(3, "ghi")));
        return demoRepository.findAll();
    }

    @GetMapping("/customers")
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/sales")
    public Sale findSales(@RequestParam("id") int id) {
        return saleRepository.findById(id);
    }

}
