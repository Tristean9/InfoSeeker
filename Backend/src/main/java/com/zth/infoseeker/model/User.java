/*
author: 请问
2024/1/18  22:45

*/
package com.zth.infoseeker.model;

// 假设您已经有了lombok依赖来简化这个类
import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private String role;
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

