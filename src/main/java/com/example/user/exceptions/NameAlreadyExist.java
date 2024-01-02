package com.example.user.exceptions;

public class NameAlreadyExist extends RuntimeException {

    public NameAlreadyExist(String name) {
        super("이미 존재하는 이름 : " + name);
    }
}
