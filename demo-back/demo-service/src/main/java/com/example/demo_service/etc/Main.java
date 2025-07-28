package com.example.demo_service.etc;

import com.example.demo_service.shop.Customer;
import com.example.demo_service.shop.Sale;

public class Main {
    public static void main(String[] args) {
        System.out.println(Sale
                .builder()
                .id(0)
                .price(1000)
                .build());
        System.out.println(Customer
                .builder()
                .id(0)
                .name("ゲスト")
                .build());

        Sale sale1 = new Sale();
        sale1.setId(1);
        sale1.setPrice(1000);
        Sale sale2 = new Sale(2, 1500, null);
        Sale sale3 = Sale.builder().id(1).price(1000).build();

        if (sale1 == sale1) {
            System.out.println("sale1とsale1は同一");
        }
        if (sale1 == sale3) {
            System.out.println("sale1とsale3は同一");
        }
        if (sale1.equals(sale1)) {
            System.out.println("sale1とsale1は同値");
        }
        if (sale1.equals(sale3)) {
            System.out.println("sale1とsale3は同値");
        }
        if (sale1.equals(sale2)) {
            System.out.println("sale1とsale2は同値");
        }
        System.out.println(
                String.format("id: %d, price: %d, customer: %s",
                        sale1.getId(), sale1.getPrice(), sale1.getCustomer()));

        // フォームの配列いっぱいテスト用
        // for (int i = 0; i < 10000; i++) {
        // System.out.printf("sales[%d]=&", i);
        // }
    }
}
