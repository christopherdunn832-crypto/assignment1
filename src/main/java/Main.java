import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        NobelQueryService service =
                new NobelQueryService();

        System.out.println("===== Nobel Prize Queries =====");
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

                int result =
                        service.countLaureatesByCategory(
                                category,
                                startYear,
                                endYear);

                System.out.println(
                        "Total Laureates = " + result);

                break;

            case 2:

                System.out.print("Keyword: ");
                String keyword = sc.nextLine();

                int count =
                        service.countKeyword(keyword);

                System.out.println(
                        "Total Laureates = " + count);

                break;

            case 3:

                System.out.print("First Name: ");
                String firstName = sc.nextLine();

                System.out.print("Last Name: ");
                String lastName = sc.nextLine();

                service.findLaureateRecord(
                        firstName,
                        lastName);

                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}