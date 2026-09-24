import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class WorkshopApplication {

    static final int MAX_STANDARD_WORKSHOPS = 5;
    static String participantName;

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

        boolean running = true;

        while (running) {
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
                    manageWorkshops(input);
                    break;

                case 2:
                    System.out.println("2. Manage participants");
                    registerParticipant(input);
                    break;

                case 3:
                    System.out.println("3. Register participant for workshop");
                    searchParticipant(input);
                    break;

                case 4:
                    System.out.println("4. Search record for participant or workshop");
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

    static void manageWorkshops(Scanner input) {
        System.out.println("\n---- Enter Workshop Details ----");

        System.out.println(("WorkshopID: "));
        int workshopId = input.nextInt();
        input.nextLine();

        System.out.print("Workshop title: ");
        String workshopTitle = input.nextLine();
        workshopTitle = formatName(workshopTitle).toUpperCase(Locale.ROOT);

        System.out.print("Facilitator name: ");
        String facilitatorName = input.nextLine();
        facilitatorName =formatName(facilitatorName);

        System.out.print("Workshop date (YYYY-MM-DD): ");
        String workshopDate = input.nextLine();

        System.out.print("Workshop fee: R");
        double workshopFee = input.nextDouble();

        System.out.print("Maximum capacity: ");
        int maximumCapacity = input.nextInt();

        System.out.print("Number of registrations so far: ");
        int numberOfRegistrations = input.nextInt();
        input.nextLine();

        int availableSpaces = maximumCapacity - numberOfRegistrations;

        System.out.println("\n---- Workshop Snapshot ----");
        System.out.println("Workshop ID        : " + workshopId);
        System.out.println("Title              : " + workshopTitle);
        System.out.println("Facilitator        : " + facilitatorName);
        System.out.println("Date               : " + workshopDate);
        System.out.println("Fee                : R" + workshopFee);
        System.out.println("Capacity           : " + maximumCapacity);
        System.out.println("Registrations      : " + numberOfRegistrations);
        System.out.println("Available Spaces   : " + availableSpaces);
        System.out.println("Max Standard Slots : " + MAX_STANDARD_WORKSHOPS);

        if (availableSpaces <= 0) {
            System.out.println("Note: This workshop is currently full.");

        } else {
            System.out.println("Note: " + availableSpaces + " space(s) available.");
        }

        System.out.println("\n------------------------------\n");
    }

    static void registerParticipant(Scanner input) {


        System.out.println("\n---- Enter Participant Details ----");

        System.out.print("Participant ID: ");
        int participantId = input.nextInt();
        input.nextLine();

        System.out.print("Participant name: ");
        participantName = input.nextLine();
        participantName = formatName(participantName);

        System.out.print("Participant email: ");
        String participantEmail = input.nextLine();
        participantEmail = participantEmail.trim().toLowerCase();

        while (!isValidEmail(participantEmail)) {
            System.out.println("Invalid email address.");
            System.out.print("Participant email: ");
            participantEmail = input.nextLine();
            participantEmail = participantEmail.trim().toLowerCase();
        }

        String registrationStatus = captureRegistrationStatus(input);

        System.out.println("\n---- Participant Snapshot ----");
        System.out.println("Participant ID     : " + participantId);
        System.out.println("Name               : " + participantName);
        System.out.println("Email              : " + participantEmail);
        System.out.println("Status             : " + registrationStatus);

        System.out.println("\n------------------------------\n");
    }

    static void searchParticipant(Scanner input) {
        System.out.println("\n---- Enter Participant Name ----");
        String searchName = input.nextLine();
        boolean foundParticipant = nameMatches(participantName, searchName);
        System.out.println(participantName);


    }

    static boolean nameMatches(String storedName, String searchTerm) {
        return storedName.toLowerCase().contains(searchTerm.toLowerCase());
    }

    static String[] cleanInput(String input) {
        String trimmed = input.trim();
        String[] words = trimmed.split("\\s+"); //regex for one or more whitespace characters, treated as a single split point.

        return words;
    }

    static String formatName(String nameInput) {
        String[] names = cleanInput(nameInput);

        StringBuilder formattedName = new StringBuilder();
        for (String name : names) {
            formattedName.append(Character.toUpperCase(name.charAt(0)));
            formattedName.append(name.substring(1).toLowerCase());
            formattedName.append(" ");
        }

        return formattedName.toString().trim();
    }

    static boolean isValidEmail(String input) {
        int atIndex = input.indexOf('@');
        if (atIndex == -1) {
            return false;
        }

        if (atIndex == 0) {
            return false;
        }

        String domainPart = input.substring(atIndex + 1);
        if (domainPart.isEmpty() || !domainPart.contains(".") || domainPart.endsWith(".")) {
            return false;
        }

        return true;
    }

    static String captureRegistrationStatus(Scanner input) {
        String status;

        while (true) {
            System.out.print("Registration status (Confirmed/Pending/Cancelled): ");
            status = input.nextLine().trim();

            if (status.equalsIgnoreCase("confirmed")
                    || status.equalsIgnoreCase("pending")
                    || status.equalsIgnoreCase("cancelled")) {
                break;
            }
            System.out.println("Invalid status. Please enter Confirmed, Pending, or Cancelled.");
        }

        // normalize to a consistent stored format
        return status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
    }
}


