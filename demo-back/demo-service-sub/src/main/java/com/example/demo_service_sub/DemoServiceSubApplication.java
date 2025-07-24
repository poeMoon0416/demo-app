package com.example.demo_service_sub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// これがないとCORSエラー出るがログインページにリダイレクトされた後ログインページのCORS設定がないからそれに引っかかっているのだと思う
// (これ消す場合はログインページにリダイレクトされないようAPIトークンとか送る必要がある)
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
// @SpringBootApplication
public class DemoServiceSubApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceSubApplication.class, args);
	}

}
