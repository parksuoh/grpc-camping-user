package com.example.user.dtos;

public record LoginResponseDto(
        String name,
        String role,
        String token
) {}
