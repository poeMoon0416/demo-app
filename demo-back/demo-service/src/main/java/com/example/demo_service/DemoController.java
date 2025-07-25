package com.example.demo_service;

import org.springframework.web.bind.annotation.RestController;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// SpringBoot3プログラミング入門等
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DemoController {
        @Autowired
        private DemoRepository demoRepository;

        // @Autowired
        // customerService customerService;

        @Autowired
        private CustomerService customerService;

        // @Autowired
        // SaleRepository saleRepository;

        @Autowired
        private SaleService saleService;

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
                List<Customer> customers = customerService.findAll();
                // https://docs.oracle.com/javase/jp/21/docs/api/java.base/java/util/stream/Stream.html
                return customers.stream()
                                .map(customer -> Customer.builder()
                                                .id(customer.getId())
                                                .name(customer.getName())
                                                // .name(null)
                                                .sales(customer.getSales()
                                                                .stream()
                                                                .map(sale -> Sale.builder()
                                                                                .id(sale.getId())
                                                                                .price(sale.getPrice())
                                                                                .build())
                                                                .toList())
                                                .build())
                                .toList();
        }

        @GetMapping("/customer")
        public Customer findCustomer(@RequestParam("id") int id) {
                Customer customer = customerService.findById(id);
                return Customer.builder()
                                .id(customer.getId())
                                .name(customer.getName())
                                .sales(customer.getSales()
                                                .stream()
                                                .map(sale -> Sale.builder()
                                                                .id(sale.getId())
                                                                .price(sale.getPrice())
                                                                .customer(null)
                                                                .build())
                                                .toList())
                                .build();
        }

        @GetMapping("/sales")
        public Sale findSales(@RequestParam("id") int id) {
                Sale sale = saleService.findById(id);
                return Sale.builder()
                                .id(sale.getId())
                                .price(sale.getPrice())
                                .customer(Customer.builder()
                                                .id(sale.getCustomer().getId())
                                                .name(sale.getCustomer().getName())
                                                .build())
                                .build();
        }

        @GetMapping("/sales-view/{sales-view-id}")
        public SaleView findSalesView(@PathVariable("sales-view-id") int id) {
                Sale sale = saleService.findById(id);
                return SaleView.builder()
                                .id(sale.getId())
                                .price(sale.getPrice())
                                .customerName(sale.getCustomer().getName())
                                .build();
        }

}
