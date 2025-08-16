package com.example.demo_service.etc;

/**
 * レコードクラスの検証
 */
public class Main2 {
    public static void main(String[] args) {
        System.out.println();
        /*
         * 単体で検証
         */
        // 引数なしコンストラクタはなし
        // Person person1 = new Person();
        Person person1 = new Person(1, "one@example.com", 120, "ゲームが好きです。", "宮城県");
        // セッターはなし
        // person1.setId(2);
        System.out.println(person1);
        System.out.println(String.format("{ID: %d, EMAIL: %s, WEIGHT: %d, PROFILE: %s, PREFECTURE: %s}",
                person1.id(), person1.email(), person1.weight(), person1.profile(), person1.prefecture()));
        person1.greet();
        System.out.println();

        /*
         * 比較して検証
         */
        Person person2 = new Person(1, "one@example.com", 120, "ゲームが好きです。", "宮城県");
        Person person3 = new Person(3, "three@example.com", 40, "プログラミングが好きです。", "東京都");
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
        Person person4 = new Person(4, "example", 39, "   ", "北海");
        System.out.println(person4);

        /*
         * @Builder, 実質引数なしコンストラクタみたいなこともできる(nullになる)
         */
        Person person5 = Person.builder().build();
        System.out.println(person5);
        Person person6 = Person.builder()
                .email("@example.com")
                .weight(121)
                .build();
        System.out.println(person6);
    }
}

// レコードクラスを継承しようとするとコンパイルエラー
// class SubPerson extends Person {}