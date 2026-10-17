import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

import nobel.Nobel;
import nobel.NobelServiceGrpc;

public class NobelClient {

    public static void main(String[] args) {

        ManagedChannel channel =
                ManagedChannelBuilder
                        .forAddress("localhost", 9090)
                        .usePlaintext()
                        .build();

        NobelServiceGrpc.NobelServiceBlockingStub stub =
                NobelServiceGrpc.newBlockingStub(channel);

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Query 1");
        System.out.println("2. Query 2");
        System.out.println("3. Query 3");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:

                System.out.print("Category: ");
                String category = sc.nextLine();

                System.out.print("Start Year: ");
                int startYear = sc.nextInt();

                System.out.print("End Year: ");
                int endYear = sc.nextInt();

                Nobel.CategoryRequest categoryRequest =
                        Nobel.CategoryRequest.newBuilder()
                                .setCategory(category)
                                .setStartYear(startYear)
                                .setEndYear(endYear)
                                .build();

                Nobel.CountResponse categoryResponse =
                        stub.countLaureatesByCategory(
                                categoryRequest);

                System.out.println(
                        "Total Laureates = "
                                + categoryResponse.getCount());

                break;

            case 2:

                System.out.print("Keyword: ");
                String keyword = sc.nextLine();

                Nobel.KeywordRequest keywordRequest =
                        Nobel.KeywordRequest.newBuilder()
                                .setKeyword(keyword)
                                .build();

                Nobel.CountResponse keywordResponse =
                        stub.countByKeyword(
                                keywordRequest);

                System.out.println(
                        "Total Laureates = "
                                + keywordResponse.getCount());

                break;

            case 3:

                System.out.print("First Name: ");
                String firstName = sc.nextLine();

                System.out.print("Last Name: ");
                String lastName = sc.nextLine();

                Nobel.LaureateRequest laureateRequest =
                        Nobel.LaureateRequest.newBuilder()
                                .setFirstName(firstName)
                                .setLastName(lastName)
                                .build();

                Nobel.LaureateResponse laureateResponse =
                        stub.findLaureate(
                                laureateRequest);

                System.out.println(
                        "Year: "
                                + laureateResponse.getYear());

                System.out.println(
                        "Category: "
                                + laureateResponse.getCategory());

                System.out.println(
                        "Motivation: "
                                + laureateResponse.getMotivation());

                break;
        }

        channel.shutdown();
    }
}