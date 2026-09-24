import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

public class WorkshopApplication {

    static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\nSKILLS WORKSHOP MANAGEMENT SYSTEM");
        System.out.println("Developed by: Ivan Zigirumugabe");
        System.out.println("Version: 1.0");
        System.out.println("Date: " + DateFormat.getDateInstance().format(new Date()) + "\n");
        System.out.println("================================================");
        System.out.println("Welcome to the Skills Workshop Management System");
        System.out.println("================================================");


        String[] menuOption = {
                "1. Manage workshops",
                "2. Manage participants",
                "3. Register participant for workshop",
                "4. Search Records",
                "5. Display registration summary",
                "6. Export report",
                "7. Load saved data",
                "8. Database operations",
                "9. Exit"
        };

        for (String option : menuOption) {
            System.out.println(option);
        }

        System.out.println("Please choose an option(type a number):");
        int selectedOption = input.nextInt();
        input.nextLine();

        System.out.println("\nSelected option:");
        switch (selectedOption) {
            case 1:
                System.out.println("1. Manage workshops");
                break;

            case 2:
                System.out.println("2. Manage participants");
                break;

            case 3:
                System.out.println("3. Register participant for workshop");
                break;

            case 4:
                System.out.println("4. Search record for participant for workshop");
                break;

            case 5:
                System.out.println("5. Display registration summary");
                break;

            case 6:
                System.out.println("6. Export report");
                break;

            case 7:
                System.out.println("7. Load saved data");
                break;

            case 8:
                System.out.println("8. Database operations");
                break;

            case 9:
                System.exit(0);
                break;
        }
    }
}
