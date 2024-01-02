package com.example.user.applications.grpcclient;

import com.example.grpc.AddCartRequest;
import com.example.grpc.AddCartResponse;
import com.example.grpc.AddCartServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcAddCartService {

    private AddCartServiceGrpc.AddCartServiceBlockingStub addCartServiceBlockingStub;
    private ManagedChannel channel;

    public GrpcAddCartService(@Value("${cart.grpc.host}") String grpcHost, @Value("${cart.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }


    public boolean addCart(Long userId){

        AddCartRequest addCartRequest = AddCartRequest.newBuilder()
                .setUserId(userId)
                .build();

        addCartServiceBlockingStub = AddCartServiceGrpc.newBlockingStub(channel);

        AddCartResponse addCartResponse = addCartServiceBlockingStub.addCart(addCartRequest);

        boolean result = addCartResponse.getOk();

        return result;
    }

}
