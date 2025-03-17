package org.example.serviceauth.controller;

import org.example.serviceauth.data.RefreshTokenRequest;
import org.example.serviceauth.data.TokenResponse;
import org.example.serviceauth.data.User;
import org.example.serviceauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.checkUser(username, password)
                .map(jwt -> {
                    ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8081/recommendation-list");
                    modelAndView.addObject("jwt", jwt);

                    System.out.println("JWT Stored in Session: " + jwt);
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
    public ModelAndView signUp(@RequestParam("username") String username, @RequestParam("password") String password) {
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
            String ans = loginService.getUsernameFromToken(requestBody.get("jwt"));
            return ResponseEntity.ok(ans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect");
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String newAccessToken = loginService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(new TokenResponse(newAccessToken));
    }

}
