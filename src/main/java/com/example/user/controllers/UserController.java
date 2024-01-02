package com.example.user.controllers;


import com.example.user.applications.AuthUserService;
import com.example.user.applications.LoginService;
import com.example.user.applications.RegisterService;
import com.example.user.dtos.AuthTokenDto;
import com.example.user.dtos.AuthUserDto;
import com.example.user.dtos.LoginRequestDto;
import com.example.user.dtos.LoginResponseDto;
import com.example.user.dtos.RegisterRequestDto;
import com.example.user.dtos.RegisterResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final RegisterService registerService;
    private final LoginService loginService;
    private final AuthUserService authUserService;

    public UserController(RegisterService registerService, LoginService loginService, AuthUserService authUserService) {
        this.registerService = registerService;
        this.loginService = loginService;
        this.authUserService = authUserService;
    }



    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        String token = registerService.register(registerRequestDto.name(), registerRequestDto.password());
        return new RegisterResponseDto(token);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto.name(), loginRequestDto.password());
    }

    @PostMapping("/auth")
    public AuthUserDto login(@Valid @RequestBody AuthTokenDto authTokenDto) {
        return authUserService.auth(authTokenDto.token());
    }


}
