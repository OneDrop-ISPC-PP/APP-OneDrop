package com.example.one_drop_cruds.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    private final String SALT = BCrypt.gensalt();
    public String encodePassword(String inputPassword){
        return BCrypt.hashpw(inputPassword, SALT);
    }
    public Boolean verifyPassword (String attemptPassword, String encodedPassword){
        return BCrypt.checkpw(attemptPassword, encodedPassword);
    }
}
