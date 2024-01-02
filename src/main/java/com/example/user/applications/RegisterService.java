package com.example.user.applications;

import com.example.user.domains.User;
import com.example.user.exceptions.NameAlreadyExist;
import com.example.user.repositories.UserRepository;
import com.example.user.security.AccessTokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.user.domains.Role.ROLE_USER;

@Service
@Transactional
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenGenerator accessTokenGenerator;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccessTokenGenerator accessTokenGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenGenerator = accessTokenGenerator;

    }


    public String register(String name, String password) {

        if(userRepository.existsByName(name)) {
            throw new NameAlreadyExist(name);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(name, encodedPassword, ROLE_USER);
        userRepository.save(user);

        // TODO: cart 추가 이벤트 grpc 전송

        String accessToken = accessTokenGenerator.generate(user.name(), user.role());

        return accessToken;

    }
}
