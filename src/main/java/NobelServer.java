import io.grpc.Server;
import io.grpc.ServerBuilder;

public class NobelServer {

    public static void main(String[] args) throws Exception {

        Server server =
                ServerBuilder.forPort(9090)
                        .addService(new NobelServiceImpl())
                        .build();

        server.start();

        System.out.println("gRPC Server running on port 9090");

        server.awaitTermination();
    }
}