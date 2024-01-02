package com.example.user.security;

import com.example.user.domains.User;
import com.example.user.dtos.AuthInfoDto;
import com.example.user.dtos.AuthUserDto;
import com.example.user.exceptions.UserNotExist;
import com.example.user.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessTokenService {
    private final AccessTokenGenerator accessTokenGenerator;
    private final UserRepository userRepository;

    public AccessTokenService(AccessTokenGenerator accessTokenGenerator, UserRepository userRepository) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.userRepository = userRepository;
    }

    public Authentication authenticate(String accessToken) {
        if (!accessTokenGenerator.verify(accessToken)) {
            return null;
        }

        AuthInfoDto tokenInfo = accessTokenGenerator.getTokenInfo(accessToken);

        User user = userRepository.findByName(tokenInfo.name()).orElseThrow(UserNotExist::new);

        AuthUserDto authUser = new AuthUserDto(user.name(), user.role().toString(), accessToken);

        Authentication authentication = UsernamePasswordAuthenticationToken
                .authenticated( authUser, null, List.of(authUser::role));

        return authentication;
    }

    public Optional<AuthUserDto> getAuthUser (String name, String accessToken){
        User user = userRepository.findByName(name).get();

        return Optional.of(new AuthUserDto(user.name(), user.role().toString(), accessToken));
    }

}