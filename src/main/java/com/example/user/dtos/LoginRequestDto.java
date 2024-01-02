package com.example.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String password
){}

