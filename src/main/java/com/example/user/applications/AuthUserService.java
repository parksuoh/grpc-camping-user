package com.example.user.applications;

import com.example.user.domains.User;
import com.example.user.dtos.AuthInfoDto;
import com.example.user.dtos.AuthUserDto;
import com.example.user.exceptions.NameNotExist;
import com.example.user.exceptions.TokenNotAvailable;
import com.example.user.repositories.UserRepository;
import com.example.user.security.AccessTokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthUserService {

    private final AccessTokenGenerator accessTokenGenerator;
    private final UserRepository userRepository;


    public AuthUserService(AccessTokenGenerator accessTokenGenerator, UserRepository userRepository) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.userRepository = userRepository;
    }


    public AuthUserDto auth(String token) {

        boolean isTokenOk = accessTokenGenerator.verify(token);

        if (!isTokenOk) {
            throw new TokenNotAvailable();
        }

        AuthInfoDto tokenInfo = accessTokenGenerator.getTokenInfo(token);

        User user = userRepository
                .findByName(tokenInfo.name())
                .orElseThrow(NameNotExist::new);

        String accessToken = accessTokenGenerator.generate(user.name(), user.role());

        return new AuthUserDto(user.name(), user.role().toString(), accessToken);
    }
}
