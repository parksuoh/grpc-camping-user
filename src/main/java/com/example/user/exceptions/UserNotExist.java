package com.example.user.exceptions;

public class UserNotExist extends RuntimeException {

    public UserNotExist() {
        super("유저가 존재하지 않습니다.");
    }
}
