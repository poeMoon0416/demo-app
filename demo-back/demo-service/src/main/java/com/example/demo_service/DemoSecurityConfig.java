package com.example.demo_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // CSRFトークン無効化, これをコメントアウトするとキチンと403でCSRFが防がれる
        httpSecurity.csrf(csrf -> {
            csrf
                    .disable();
        });
        // デフォルト設定の明示
        // httpSecurity.csrf(Customizer.withDefaults());

        // 全てのページで認証(指定なしだと全ページ認証不要になる)
        // 参考URL: https://qiita.com/TBATYOF/items/4e87a6297cc78f43e842, 999エラー
        // SpringSecurityはなるべく設定を明示するようにした方が良いのかも
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize
                    // /loginと/logoutはデフォルトで許可されている
                    // WhiteLabelErrorページは許可にしないとログイン成功時に999エラー
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/login-page").permitAll()
                    .anyRequest().authenticated();
        });

        // BASIC認証(IDとパスワード)
        // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html
        httpSecurity.httpBasic(Customizer.withDefaults());

        // ログイン成功時"/"に遷移(指定なしだと403エラーになる, 成功時の挙動が未定義だから？)
        // ただしログイン成功時にログアウト状態で最後にアクセスしたURLが存在するならそれが優先される(正確にはログインにリダイレクトされる前のURL)
        // ログインのダイアログはどのページでもログイン失敗すると表示されるので恐らくブラウザが401 Unauthorizeで出しているもの
        httpSecurity.formLogin(form -> {
            form
                    .loginPage("/login-page")
                    .defaultSuccessUrl("/");
        });

        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        // ユーザー作成, デフォルトだとuser:<ログのパスワード>がつくられる
        return new InMemoryUserDetailsManager(
                User
                        .withUsername("guest")
                        .password(
                                // String直接指定するとパスワードエンコーダーを使うようにしろという例外発生
                                PasswordEncoderFactories
                                        .createDelegatingPasswordEncoder()
                                        .encode("qwerty"))
                        .build());
    }
}
