package com.example.demo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Set-CookieでセッションID, WWW-AuthenticateでBASIC認証が確認できる
// https://docs.spring.io/spring-security/reference/servlet/index.html, SpringSecurity公式
// curl -v http://localhost:8080/create-user
// https://developer.mozilla.org/ja/docs/Glossary/Base64, バイナリをテキストにするの話
// await fetch("http://localhost:8080", {headers: {Autorization: btoa("")}});
// APIテスター: Add authorization
// ---
// 401 Unauthorized: クレデンシャルが異なる or CSRFトークン or CORS設定
// ---
// 参考URL: https://penguinlabo.hatenablog.com/entry/springsecuritymukouka, https://docs.spring.io/spring-boot/reference/using/auto-configuration.html
// XxxAutoConfigurationという自動設定クラスを探せば任意の設定を無効化できるということなのだろう
// @SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class DemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceApplication.class, args);
	}

}
