package com.example.demo_service;

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
    }
}
