/*
author: 请问
2024/1/18  22:56

*/
package com.zth.infoseeker.controller;

import com.zth.infoseeker.exception.AuthenticationException;
import com.zth.infoseeker.model.User;
import com.zth.infoseeker.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");
            User user = authService.authenticate(username, password);

            // 创建一个简单的响应，包括用户角色
            Map<String, String> response = new HashMap<>();
            response.put("role", user.getRole()); // 假设User对象有一个getRole()方法

            return ResponseEntity.ok().body(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
