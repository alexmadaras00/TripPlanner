package org.example.serviceauth.controller;

import org.example.serviceauth.data.User;
import org.example.serviceauth.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    public LoginController(LoginService userService) {
    }

    @GetMapping("/")
    public String loginRoot(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(String username, String password, Model model) {
//        return userService.findByUsername(username)
//                .filter(user -> password.equals(user.getPassword()))
//                .flatMap(user -> "")
//                .switchIfEmpty(Mono.just("login").doOnNext(s -> model.addAttribute("loginError", true)));
        return "";
    }

    // other controller methods
}
