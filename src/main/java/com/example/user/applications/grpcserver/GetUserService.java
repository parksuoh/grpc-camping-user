package com.example.user.applications.grpcserver;

import com.example.grpc.GetUserRequest;
import com.example.grpc.GetUserResponse;
import com.example.grpc.GetUserServiceGrpc;
import com.example.user.domains.User;
import com.example.user.repositories.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class GetUserService extends GetUserServiceGrpc.GetUserServiceImplBase {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        GetUserResponse reply;
        String name = request.getName();

        Optional<User> user = userRepository.findByName(name);

        reply = user.isPresent() ?
                GetUserResponse.newBuilder()
                .setId(user.get().id())
                .setName(user.get().name())
                .setRole(user.get().role().toString())
                .build()
                : GetUserResponse.newBuilder()
                        .setId(0)
                        .setName("null")
                        .setRole("null")
                        .build();


        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
