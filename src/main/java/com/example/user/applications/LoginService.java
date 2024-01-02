package com.example.user.applications;

import com.example.user.domains.User;
import com.example.user.dtos.LoginResponseDto;
import com.example.user.exceptions.NameNotExist;
import com.example.user.exceptions.PasswordNotMatch;
import com.example.user.repositories.UserRepository;
import com.example.user.security.AccessTokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenGenerator accessTokenGenerator;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccessTokenGenerator accessTokenGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenGenerator = accessTokenGenerator;
    }

    public LoginResponseDto login(String name, String password){

        User user = userRepository
                .findByName(name)
                .orElseThrow(NameNotExist::new);


        boolean isPassMatch = passwordEncoder.matches(password, user.password());

        if(!isPassMatch){
            throw new PasswordNotMatch();
        }

        String accessToken = accessTokenGenerator.generate(user.name(), user.role());



        return new LoginResponseDto(user.name(), user.role().toString(), accessToken);
    }

}
