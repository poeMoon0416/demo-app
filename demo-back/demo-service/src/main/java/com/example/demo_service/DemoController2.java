package com.example.demo_service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 1つのアプリに@RestControllerと@Controllerを混在させることができる
// Spring Web Fluxへの移行等も段階的に行えると考えられる
@Controller
public class DemoController2 {
    // @ControllerでStringを返すとテンプレートを返す
    // (@RestControllerだとjsonが返ってくる)
    @GetMapping("/greet")
    public String getGreet() {
        return "greet";
    }
}
