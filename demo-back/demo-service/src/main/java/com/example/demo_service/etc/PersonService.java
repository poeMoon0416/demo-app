package com.example.demo_service.etc;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
// ロールバックされるとIDが消費される
@Transactional
@RequiredArgsConstructor
public class PersonService {
    /*
     * Springの3つのインジェクション:
     * https://spring.pleiades.io/spring-framework/reference/core/beans/dependencies
     * /factory-collaborators.html
     * 
     * ・コンストラクタインジェクション: 基本これ、定数なので後から変更できなくて安全
     * ・セッターインジェクション: 必須でないか後から依存関係の変更が生じうる場合
     * ・フィールドインジェクション: 非推奨、代入がコード中に存在せずモックに置き換えづらい・明示的でないとされている
     * 
     * 補足: コンストラクタが長くなると設計がよくない可能性があるようだ。(コードの悪臭)
     * Lombokの@RequiredConstructor等を使用するとこういった設計の質が覆い隠される可能性があるため、
     * Lombokを使わない派の人もいるのだろう。
     * 
     * 参考1: https://job-info.hateblo.jp/entry/2024/02/10/224259
     * 参考2: https://qiita.com/sakumakuma/items/c3ae6703910b07d298a0
     */
    private final PersonRepository personRepository;

    public List<Person> list() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        // @Builderを使うとセットしないフィールドにnullとかしなくてよくなる
        return personRepository.saveAndFlush(Person.builder()
                .email(person.getEmail())
                .weight(person.getWeight())
                .profile(person.getProfile())
                .prefecture(person.getPrefecture())
                // ここで作成日を入れる
                .createdDateTime(LocalDateTime.now())
                .build());
    }
}
