package com.example.demo_service.etc;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// レコードでやろうとするとコントローラーでセッターが呼ばれてうまくいかない
// Spring Boot Validation: https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html#validation-beanvalidation-spring-method-i18n
@Entity
@Table(name = "PERSONS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    Long id;

    /** メールアドレス */
    @Column(name = "EMAIL", nullable = false)
    @Email(message = "この\"メールアドレス\"は登録できません")
    String email;

    /** 体重 */
    @Column(name = "WEIGHT", nullable = false)
    // {0}はちょっと分かりにくいと思ってしまった... -> 下記より使用しない
    // Vueからも認識できない、この{0}とかってテンプレートエンジン(Thymeleaf)しかみえないのかも
    @Min(value = 40, message = "{0}は{value}以上にしてください")
    @Max(120)
    Integer weight;

    /** 自己紹介 */
    @Column(name = "PROFILE", nullable = false)
    // 注意: 全角スペースのみはすり抜ける
    @NotBlank
    String profile;

    /**
     * 出身地(都道府県)
     * Java正規表現:
     * https://docs.oracle.com/javase/jp/11/docs/api/java.base/java/util/regex/Pattern.html?is-external=true
     */
    @Column(name = "PREFECTURE", nullable = true)
    @Pattern(regexp = "^.*[都道府県]$")
    String prefecture;

    /** 作成日時 */
    @Column(name = "CREATED_DATE_TIME", nullable = false)
    @PastOrPresent
    LocalDateTime createdDateTime;
}
