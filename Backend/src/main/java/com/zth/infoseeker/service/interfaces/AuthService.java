package com.zth.infoseeker.service.interfaces;


import com.zth.infoseeker.exception.AuthenticationException;
import com.zth.infoseeker.model.User;

public interface AuthService {
    User authenticate(String username, String password) throws AuthenticationException;
}

