package com.example.user.dtos;

public record AuthUserDto(
        String name,
        String role,
        String accessToken
) {}
