package com.garagemanagement.userservice.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashedPassword {
    public static String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static boolean decryptPassword(String password, String hashedPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }
}
