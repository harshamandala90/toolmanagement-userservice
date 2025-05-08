package com.taskmanagement.tool.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Passwordutil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }
}