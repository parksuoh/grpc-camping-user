package com.example.user.exceptions;

public class TokenNotAvailable extends RuntimeException {

    public TokenNotAvailable() {
        super("토큰이 유효하지 않습니다.");
    }
}
