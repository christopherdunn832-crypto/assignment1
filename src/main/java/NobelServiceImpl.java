import io.grpc.stub.StreamObserver;
import nobel.Nobel;
import nobel.NobelServiceGrpc;

public class NobelServiceImpl
        extends NobelServiceGrpc.NobelServiceImplBase {

    private final NobelQueryService service =
            new NobelQueryService();

    @Override
    public void countByKeyword(
            Nobel.KeywordRequest request,
            StreamObserver<Nobel.CountResponse> responseObserver) {

        int result =
                service.countKeyword(
                        request.getKeyword());

        Nobel.CountResponse response =
                Nobel.CountResponse.newBuilder()
                        .setCount(result)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void countLaureatesByCategory(
            Nobel.CategoryRequest request,
            StreamObserver<Nobel.CountResponse> responseObserver) {

        int result =
                service.countLaureatesByCategory(
                        request.getCategory(),
                        request.getStartYear(),
                        request.getEndYear());

        Nobel.CountResponse response =
                Nobel.CountResponse.newBuilder()
                        .setCount(result)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void findLaureate(
            Nobel.LaureateRequest request,
            StreamObserver<Nobel.LaureateResponse> responseObserver) {

        LaureateRecord result =
                service.findLaureateRecord(
                        request.getFirstName(),
                        request.getLastName());

        Nobel.LaureateResponse response =
                Nobel.LaureateResponse.newBuilder()
                        .setYear(result.year())
                        .setCategory(result.category())
                        .setMotivation(result.motivation())
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}