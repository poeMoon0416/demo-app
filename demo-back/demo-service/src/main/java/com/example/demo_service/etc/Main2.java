package com.example.demo_service.etc;

import java.time.LocalDateTime;

/**
 * レコードクラスの検証
 */
public class Main2 {
    public static void main(String[] args) {
        System.out.println();
        LocalDateTime now = LocalDateTime.now();
        /*
         * 単体で検証
         */
        // 引数なしコンストラクタはなし
        // Person person1 = new Person();
        PersonRecord person1 = new PersonRecord(1L, "one@example.com", 120, "ゲームが好きです。", "宮城県", now);
        // セッターはなし
        // person1.setId(2);
        System.out.println(person1);
        System.out.println(
                String.format("{ID: %d, EMAIL: %s, WEIGHT: %d, PROFILE: %s, PREFECTURE: %s, CREATED_DATE_TIME: %s}",
                        person1.id(), person1.email(), person1.weight(), person1.profile(), person1.prefecture(),
                        person1.createdDateTime()));
        person1.greet();
        System.out.println();

        /*
         * 比較して検証
         */
        PersonRecord person2 = new PersonRecord(1L, "one@example.com", 120, "ゲームが好きです。", "宮城県", now);
        PersonRecord person3 = new PersonRecord(3L, "three@example.com", 40, "プログラミングが好きです。", "東京都", now);
        // 同一性
        System.out.println(person1 == person1 ? "person1とperson1は同一です" : "person1とperson1は同一ではありません");
        System.out.println(person1 == person2 ? "person1とperson2は同一です" : "person1とperson2は同一ではありません");
        // 同値性
        System.out.println(person1.equals(person2) ? "person1とperson2は同値です" : "person1とperson2は同値ではありません");
        System.out.println(person1.equals(person3) ? "person1とperson3は同値です" : "person1とperson3は同値ではありません");
        System.out.println();

        /*
         * インスタンス化するタイミングではバリデーションが走らない(@Validatedのタイミングで走る)
         */
        PersonRecord person4 = new PersonRecord(4L, "example", 39, "   ", "北海", now);
        System.out.println(person4);

        /*
         * @Builder, 実質引数なしコンストラクタみたいなこともできる(nullになる)
         */
        PersonRecord person5 = PersonRecord.builder().build();
        System.out.println(person5);
        PersonRecord person6 = PersonRecord.builder()
                .email("@example.com")
                .weight(121)
                .build();
        System.out.println(person6);
    }
}

// レコードクラスを継承しようとするとコンパイルエラー
// class SubPerson extends Person {}