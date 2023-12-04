package com.example.SpringBootWebApp.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/hello-world")
    public String sayHello() {
        return "hello_world";
    }
    @GetMapping("/hello-world/my-profile")
    public String getMyProfile() {
        return "myProfile.html";
    }


}
