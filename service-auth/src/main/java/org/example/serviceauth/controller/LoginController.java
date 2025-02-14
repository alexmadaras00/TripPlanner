package org.example.serviceauth.controller;

import org.example.serviceauth.data.User;
import org.example.serviceauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
  
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.checkUser(username, password)
                .map(jwt -> {
                    ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8081/recommendation-list");
                    modelAndView.addObject("jwt", jwt);
                    return modelAndView;
                })
                .onErrorResume(e -> {
                    ModelAndView modelAndView = new ModelAndView("login");
                    modelAndView.addObject("error", e.getMessage());
                    return Mono.just(modelAndView);
                }).block();
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView singUp(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (!loginService.saveUser(new User(username, password))) {
            ModelAndView modelAndView = new ModelAndView("signup");
            modelAndView.addObject("error", "Username already exists");
            return modelAndView;
        }
        return new ModelAndView("login");
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody Map<String, String> requestBody) {
        try {
            String ans = LoginService.getUsernameFromToken(requestBody.get("jwt"));
            return ResponseEntity.ok(ans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect");
        }
    }
}
