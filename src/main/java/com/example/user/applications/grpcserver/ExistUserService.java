package com.example.user.applications.grpcserver;

import com.example.grpc.ExistUserRequest;
import com.example.grpc.ExistUserResponse;
import com.example.grpc.ExistUserServiceGrpc;
import com.example.user.repositories.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ExistUserService extends ExistUserServiceGrpc.ExistUserServiceImplBase {

    private final UserRepository userRepository;

    public ExistUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void existUser(ExistUserRequest request, StreamObserver<ExistUserResponse> responseObserver) {
        String name = request.getName();
        boolean res = userRepository.existsByName(name);

        ExistUserResponse reply = ExistUserResponse.newBuilder()
                .setOk(res)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
