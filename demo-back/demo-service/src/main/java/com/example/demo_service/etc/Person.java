package com.example.demo_service.etc;

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
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

/**
 * 個人情報(バリデーションテスト用)
 * 
 * バリデーション:
 * https://spring.pleiades.io/specifications/platform/10/apidocs/jakarta/validation/constraints/package-summary
 * 
 * レコードクラス:
 * https://docs.oracle.com/javase/jp/15/language/records.html
 * ・イミュータブルで継承不可(finalクラス, setterなし)
 * ・全引数のコンストラクタがある(@AllArgsConstructorに対応, 引数なしコンストラクタはなし)
 * ・finalフィールドとgetterがある
 * ・equals(), hashCode(), toString()がある(セッター除いて@Dataに対応)
 * 注意: レコードだとIDEでうまくフィールドのjavadocが出てきてくれない...,
 * イミュータブルでないけどLombokの@Dataでやった方が良さそうかも。
 * 
 * VSCのワークスペースからD:\ユーザーデータ\OneDrive\デスクトップ\vsc\demo-app\demo-back\demo-service\bin\main\com\example\demo_service\etc\Person.classを確認すればバイトコードから生成されたものが確認できる
 */
@Entity
@Table(name = "PERSON")
// @NoArgsConstructorはクラスか列挙型でないと対応していない
// @NoArgsConstructor
@Builder
public record Person(
                /** ID */
                @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID", nullable = false) Integer id,
                /** メールアドレス */
                @Column(name = "EMAIL", nullable = false) @Email String email,
                /** 体重 */
                @Column(name = "WEIGHT", nullable = false) @Min(40) @Max(120) Integer weight,
                /** 自己紹介 */
                @Column(name = "PROFILE", nullable = false) @NotBlank String profile,
                /**
                 * 出身地(都道府県)
                 * Java正規表現:
                 * https://docs.oracle.com/javase/jp/11/docs/api/java.base/java/util/regex/Pattern.html?is-external=true
                 */
                @Column(name = "PREFECTURE", nullable = true) @Pattern(regexp = "^.*[都道府県]$") String prefecture) {
        /**
         * 挨拶メソッド、メソッドを書ける
         */
        public void greet() {
                System.out.println(prefecture + "出身です。" + "自己紹介: " + profile);
        }
}
