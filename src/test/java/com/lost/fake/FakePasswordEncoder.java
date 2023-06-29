package com.lost.fake;

import org.springframework.security.crypto.password.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword + "abcdefghijklmnopqrstuvwxyz1234567890";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return (rawPassword + "abcdefghijklmnopqrstuvwxyz1234567890").equals(encodedPassword);
    }
}
