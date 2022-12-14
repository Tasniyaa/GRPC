package GRPC_Server;


import User.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9090).addService (new UserService()).build();
        server.start();
        System.out.println("Server has started at port - " + server.getPort());
        server.awaitTermination();
    }
}
