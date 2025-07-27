package com.example.demo_service.shop;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顧客情報
 */
@Entity
@Table(name = "CUSTOMERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Column(nullable = false)でもエンティティのフィールドはnullになりうる
    // (DB側の列がNOT NULLであるという意味)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "customer")
    @Column(nullable = true)
    @JsonInclude(Include.NON_NULL)
    private List<Sale> sales;
}
