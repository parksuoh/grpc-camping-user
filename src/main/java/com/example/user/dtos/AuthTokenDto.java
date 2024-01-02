package com.example.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthTokenDto(
        @NotBlank
        String token
) {}

