package com.example.user.applications.grpcserver;

import com.example.grpc.TokenAuthServiceGrpc;
import com.example.grpc.TokenRequest;
import com.example.grpc.TokenResponse;
import com.example.user.domains.User;
import com.example.user.dtos.AuthInfoDto;
import com.example.user.repositories.UserRepository;
import com.example.user.security.AccessTokenGenerator;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class TokenAuthService extends TokenAuthServiceGrpc.TokenAuthServiceImplBase {

    private final AccessTokenGenerator accessTokenGenerator;
    private final UserRepository userRepository;

    public TokenAuthService(AccessTokenGenerator accessTokenGenerator, UserRepository userRepository) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.userRepository = userRepository;
    }

    @Override
    public void tokenAuth(TokenRequest request, StreamObserver<TokenResponse> responseObserver) {
        String token = request.getToken();
        TokenResponse reply;

        AuthInfoDto tokenInfo = accessTokenGenerator.getTokenInfo(token);

        Optional<User> user = userRepository
                .findByName(tokenInfo.name());


        reply = user.isPresent() ?
                TokenResponse.newBuilder()
                        .setName(user.get().name())
                        .setRole(user.get().role().toString())
                        .build()
                : TokenResponse.newBuilder()
                        .setName("null")
                        .setRole("null")
                        .build() ;

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
