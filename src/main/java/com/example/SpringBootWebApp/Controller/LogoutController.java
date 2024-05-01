package com.example.SpringBootWebApp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public String getLogoutPage() {
        return "logout";
    }
    // при выполнении Post запроса на /logout spring security
    // сам перехватит этот запрос и сделает все нужные действия для выхода (удаление сессии и тд)
}
