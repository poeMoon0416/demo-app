package com.example.demo_service.shop;

import java.util.List;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
// デシリアライズ,
// JSONプロパティの過多の場合に下記の指定をしてもエラーが出ないので@RequestBodyでignoreUnkown=trueのようなことをしているのだろう
// @JsonIgnoreProperties(ignoreUnknown = false)
public class Customer {
    // https://spring.pleiades.io/specifications/platform/8/apidocs/javax/persistence/generationtype
    // IDENTITY: AUTO_INCREMENT, IDの列のようだ
    // SEQUENCE: <table>_seq, シーケンスのオブジェクトのようだ
    // TABLE: hibernate_sequences, シーケンス用のテーブルのようだ
    // AUTO: エラーメッセージ的にSEQUENCEのようだ(MySQL)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int id;
    private Integer id;

    // Column(nullable = false)でもエンティティのフィールドはnullになりうる
    // (DB側の列がNOT NULLであるという意味)
    @Column(name = "NAME", nullable = false, length = 255)
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @OneToMany(mappedBy = "customer")
    @Column(nullable = true)
    @JsonInclude(Include.NON_NULL)
    private List<Sale> sales;
}
