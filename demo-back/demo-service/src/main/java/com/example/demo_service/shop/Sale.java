package com.example.demo_service.shop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顧客ごとの販売情報
 */
@Entity
@Table(name = "SALES")
// https://qiita.com/opengl-8080/items/671ffd4bf84fe5e32557
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @JsonInclude(Include.NON_NULL)
    private Customer customer;
}
