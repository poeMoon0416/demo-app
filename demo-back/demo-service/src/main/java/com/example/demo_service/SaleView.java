package com.example.demo_service;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleView {
    @JsonProperty("売上情報ID")
    private int id;

    @JsonProperty("売上高")
    private int price;

    @JsonProperty("顧客名")
    private String customerName;
}
