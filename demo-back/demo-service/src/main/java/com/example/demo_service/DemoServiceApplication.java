package com.example.demo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 参考URL: https://penguinlabo.hatenablog.com/entry/springsecuritymukouka, https://docs.spring.io/spring-boot/reference/using/auto-configuration.html
// XxxAutoConfigurationという自動設定クラスを探せば任意の設定を無効化できるということなのだろう
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceApplication.class, args);
	}

}
