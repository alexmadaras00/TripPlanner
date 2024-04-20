package org.example.serviceauth.controller;

import org.example.serviceauth.data.User;
import org.example.serviceauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<String> singUp(@RequestBody User user) {
        if (!loginService.saveUser(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        return ResponseEntity.ok("Signed up successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            String jwt = loginService.checkUser(user);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody Map<String, String> requestBody) {
        String id = requestBody.get("id");
        String jwt = requestBody.get("jwt");
        boolean ans = LoginService.verifyJwtId(id, jwt);
        if (ans) {
            return ResponseEntity.ok("Correct");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect");
    }
}
