import io.grpc.Server;
import io.grpc.ServerBuilder;

public class NobelServer {

    public static void main(String[] args) throws Exception {

        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "8080")
        );

        Server server =
                ServerBuilder.forPort(port)
                        .addService(new NobelServiceImpl())
                        .build();

        server.start();

        System.out.println(
                "gRPC Server running on port " + port);

        server.awaitTermination();
    }
}