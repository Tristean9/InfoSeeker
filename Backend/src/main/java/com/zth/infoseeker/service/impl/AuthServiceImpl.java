/*
author: 请问
2024/1/18  22:48

*/
package com.zth.infoseeker.service.impl;

import com.zth.infoseeker.exception.AuthenticationException;
import com.zth.infoseeker.model.User;
import com.zth.infoseeker.service.interfaces.AuthService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private Map<String, User> users = new HashMap<>();

    // 这里应使用加密的密码，并且从数据库获取用户信息
    public AuthServiceImpl() {
        users.put("admin", new User("admin", "password", "ADMIN"));
        users.put("user", new User("user", "password", "USER"));
    }

    @Override
    public User authenticate(String username, String password) throws AuthenticationException {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new AuthenticationException("Invalid username or password");
    }
}
