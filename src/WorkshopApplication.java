import java.io.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

public class WorkshopApplication {

    static ArrayList<Workshop> workshopList = new ArrayList<>();
    static Map<Integer, Workshop> workshopMap = new HashMap<>();

    static Map<Integer, Participant> participantMap = new HashMap<>();
    static  Map<Integer, Set<Integer>> participantWorkshopMap = new HashMap<>();

    static Set<String> usedEmails = new HashSet<>();
    static Set<Integer> usedWorkshopIds = new HashSet<>();
    static Set<Integer> usedParticipantsIds = new HashSet<>();

    static final int MAX_STANDARD_WORKSHOPS = 5;
    static boolean hasUnsavedData = false;

    static int participantCount;
    static int workshopCount;

    static final String[] workshopTtles = {
            "Introduction to Java",
            "Web Development Fundamentals",
            "Database Design",
            "Networking Basics",
            "Cybersecurity Awareness"
    };

    enum WorkshopCategory {
        PROGRAMMING,
        DATABASE,
        NETWORKING,
        CYBERSECURITY,
        DATA_SCIENCE
    }

    public static void main(String[] args) {
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

            int selectedOption = readInt(input, "Please choose an option(type a number):");

            System.out.println("\nSelected option:");
            String option;
            switch (selectedOption) {
                case 1:
                    System.out.println("1. Manage workshops");
                    System.out.println("   a) View active workshops");
                    System.out.println("   b) Create new workshop");
                    option = input.nextLine().trim();
                    if (option.equals("a")) {

                        List<String> workshopSummaries = new ArrayList<>();

                        for (Workshop workshop : workshopList) {
                            workshopSummaries.add(workshop.workshopId + " - " + workshop.title + " (" + workshop.category + ")");
                        }

                        System.out.println("---- Quick Summary(via generic method) ----");
                        displayList(workshopSummaries);
                        System.out.println("-------------------------------------------\n");



                        int i = 0;
                        for  (Workshop workshop : workshopList) {
                            i++;
                            System.out.println(i + ". " + workshop.title);
                            workshop.printSnapshot();
                            System.out.println("\n------------------------------\n");
                        }

                    }

                    if (option.equals("b")) {
                        createWorkshops(input);
                    }

                    break;

                case 2:
                    System.out.println("2. Manage participants");
                    System.out.println("    a)View all participants");
                    System.out.println("    b)Register participant for another workshop");
                    option = input.nextLine().trim();
                    if (option.equals("a")) {

                        List<String> participantSummaries = new ArrayList<>();
                        for (Participant p : participantMap.values()) {
                            participantSummaries.add(p.participantId + " - " + p.firstName + " " + p.surname);
                        }
                        displayList(participantSummaries);

                        displayAllPartcipants();
                    }

                    if (option.equals("b")) {
                        registerExistingParticipant(input);
                    }
                    break;

                case 3:
                    System.out.println("3. Register participant for workshop");
                    registerParticipant(input);
                    break;

                case 4:
                    System.out.println("4. Search records for participants or workshops");
                    System.out.println("   a) Search participants");
                    System.out.println("   b) Search workshops");
                    System.out.print("Choose (a/b): ");
                    String searchChoice = input.nextLine().trim();
                    if (searchChoice.equals("a")) {
                        searchParticipant(input);
                    } else if (searchChoice.equals("b")) {
                        searchWorkshops(input);
                    } else {
                        System.out.println("Invalid choice");
                    }

                    break;

                case 5:
                    System.out.println("5. Display registration summary");
                    System.out.println("   a) Filter workshops by category");
                    System.out.println("   b) Filter workshops with available spaces");
                    System.out.println("   c) Calculate total expected income");
                    System.out.println("   d) Count registrations by workshop");
                    System.out.println("   e) Calculate total registrations");

                    String summaryChoice = input.nextLine().trim();

                    System.out.print("Choose (a-e): ");
                    switch (summaryChoice.toLowerCase()) {

                        case "a":
                            WorkshopCategory chosenCategory = selectWorkshopCategory(input);
                            filterWorkshopsByCategory(chosenCategory);
                            break;

                        case "b":
                            filterWorkshopsWithAvailableSpaces();
                            break;

                        case "c":
                            calculateTotalExpectedIncome();
                            break;

                        case "d":
                            countRegistrationsByWorkshop();
                            break;

                        case "e":
                            calculateTotalRegistrations();

                        default:
                            System.out.println("Invalid choice");
                    }
                    break;

                case 6:
                    System.out.println("6. Export report");
                    System.out.println("   a) Export report");
                    System.out.println("   b) Save participants and workshops to file(backup)");
                    System.out.println("Choose a/b");
                    String exportChoice = input.nextLine().trim();

                    if (exportChoice.equalsIgnoreCase("a")) {
                        exportRegistrationReport();
                        exportSummaryReport();
                    } else if (exportChoice.equalsIgnoreCase("b")) {
                        writeRegistrationsToFile();
                        writeWorkshopsToFile();

                    }

                    break;

                case 7:
                    System.out.println("7. Load saved data");
                    readParticipantsFromFile();
                    readWorkshopsFromFile();
                    break;

                case 8:
                    System.out.println("8. Database operations");
                    break;

                case 9:

                    if (hasUnsavedData) {
                        System.out.println("You have unsaved information. Exit anyway? (y/n): ");
                        String answer = input.nextLine().trim();
                        if (answer.equalsIgnoreCase("y")) {

                            System.out.println("Saving data before exit.....");
                            writeRegistrationsToFile();
                            writeWorkshopsToFile();
                            hasUnsavedData = false;

                            System.out.println("Exiting: Goodbye!");
                            running = false;
                        }
                    } else {
                        System.out.println("Exiting: Goodbye!");
                        running = false;
                    }
                    break;

                default:
                    System.out.println("Invalid choice\n");
                    System.out.println("Please choose an option(a number):");
            }


        }


    }

    static void createWorkshops(Scanner input) {
        System.out.println("\n---- Enter Workshop Details ----");

        workshopCount ++;
        int workshopId = workshopCount;

        if (!usedWorkshopIds.add(workshopId)) {
            System.out.println("Workshop ID is already in use");
            return;
        }

        System.out.println("WorkshopID: " + workshopId);

        String workshopTitle = selectWorkshopTitle(input);

        WorkshopCategory workshopCategory = selectWorkshopCategory(input);

        System.out.print("\nFacilitator First Name: ");
        String facilitatorName = input.nextLine();
        facilitatorName =formatName(facilitatorName);

        System.out.print("\nWorkshop date of creation: ");
        LocalDate workshopDate = LocalDate.now();
        System.out.println(workshopDate.toString());

        double workshopFee = readDouble(input, "\nWorkshop fee: R");
        while (workshopFee < 0) {
            System.out.println("Workshop fee can't be a negative number");
            workshopFee = readDouble(input, "Workshop fee: R");
        }

        int maximumCapacity = readInt(input, "\nMaximum capacity: ");
        while (maximumCapacity < 0) {
            System.out.println("Maximum capacity must be more than 0");
            maximumCapacity = readInt(input, "Maximum capacity: ");
        }

        int numberOfRegistrations = readInt(input, "\nNumber of registrations: ");
        input.nextLine();
        while (numberOfRegistrations < 0) {
            System.out.println("Number of registrations must be more than or equal to 0");
            numberOfRegistrations = readInt(input, "Number of registrations: ");
        }

        boolean activeStatus = true;

        int availableCapacity = 0;


        Workshop newWorkshop = new Workshop(
                workshopId,
                workshopTitle,
                workshopCategory,
                facilitatorName,
                workshopDate,
                workshopFee,
                maximumCapacity,
                numberOfRegistrations,
                availableCapacity,
                activeStatus
        );

        workshopMap.put(workshopId, newWorkshop);

        if (newWorkshop.isFull()) {
            System.out.println("Note: This workshop was created already full.");
            newWorkshop.availableCapacity = 0;
        }

        workshopList.add(newWorkshop);
        newWorkshop.printSnapshot();

        System.out.println("\n------------------------------\n");

        hasUnsavedData = true;
    }

    static void displayWorkshopTitles() {
        System.out.println("\n---- Standard Workshop Titles ----");
        for (int i = 0; i < workshopTtles.length; i++) {
            System.out.println((i + 1) + ". " + workshopTtles[i]);
        }
    }

    static String selectWorkshopTitle(Scanner input) {
        displayWorkshopTitles();

        int choice = readInt(input, "Select a workshop (1-"+workshopTtles.length+"):" );

        while (choice < 1 || choice > workshopTtles.length) {
            System.out.println("Invalid choice\n");
            System.out.println("Please select a workshop (1-"+workshopTtles.length+"):");
            choice = readInt(input, "Select a workshop (1-"+workshopTtles.length+"):" );
        }

        return workshopTtles[choice-1];
    }

    static WorkshopCategory selectWorkshopCategory(Scanner input) {
        WorkshopCategory[] categories = WorkshopCategory.values();

        System.out.println("\n---- Workshop Category Selections ----");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i].toString());
        }


        int choice = readInt(input, "Select a workshop category (1-"+categories.length+"):" );

        while (choice < 1 || choice > categories.length) {
            System.out.println("Invalid choice\n");
            choice = readInt(input, "Select a workshop category (1-"+categories.length+"):" );
        }

        return categories[choice-1];
    }

    static void registerParticipant(Scanner input) {

        System.out.println("\n---- Enter Participant Details ----");

        participantCount ++;
        int participantId = participantCount;

        if (!usedParticipantsIds.add(participantId)) {
            System.out.println("Participant ID is already in use");
            return;
        }

        System.out.println("Participant ID: " + participantId);


        System.out.print("Participant First Name: ");
        String participantName = input.nextLine();
        while(participantName == null || participantName.isEmpty()) {
            System.out.println("Invalid Participant Name");
            System.out.print("Re-enter Participant First Name: ");
            participantName = input.nextLine().trim();
        }
        participantName = formatName(participantName);

        System.out.println("Participant lastName: ");
        String participantSurname = input.nextLine();
        while(participantSurname == null || participantSurname.isEmpty()) {
            System.out.println("Invalid Participant Surname");
            System.out.println("Re-enter Participant Surname: ");
            participantSurname = input.nextLine().trim();

        }
        participantSurname = formatName(participantSurname);

        System.out.print("Participant email: ");
        String participantEmail = input.nextLine();
        participantEmail = participantEmail.trim().toLowerCase();
        while (!isValidEmail(participantEmail) || !usedEmails.add(participantEmail)) {

            if (!isValidEmail(participantEmail)) {
                System.out.println("Invalid email address.");
            } else {
                System.out.println("Email address already in use.");
            }
            System.out.print("Participant email: ");
            participantEmail = input.nextLine().trim().toLowerCase();
        }


        System.out.println("Participant phone number: ");
        String participantPhoneNumber = input.nextLine();
        participantPhoneNumber = participantPhoneNumber.trim().toLowerCase();
        while (participantPhoneNumber.length() > 10){
            System.out.println("Invalid phone number\n");
            System.out.println("Please enter a valid phone number");
            participantPhoneNumber = input.nextLine();
            participantPhoneNumber = participantPhoneNumber.trim().toLowerCase();
        }



        System.out.print("Participant type (e.g. Student/Public): ");
        String participantType = input.nextLine().trim();

        System.out.print("Is this participant active? (y/n): ");
        String activeAnswer = input.nextLine().trim();
        String activeStatus = activeAnswer.equalsIgnoreCase("y") ? "Active" : "Inactive";

        Participant newParticipant = new Participant(
                participantId,
                participantName,
                participantSurname,
                participantEmail,
                participantPhoneNumber,
                participantType,
                activeStatus
        );

        participantMap.put(participantId, newParticipant);

        newParticipant.printSnapshot();

        registerParticipantForWorkshop(input, participantId);

        System.out.println("\n------------------------------\n");

        hasUnsavedData = true;
    }

    static void registerExistingParticipant(Scanner input) {
        if (participantMap.isEmpty()) {
            System.out.println("No participants exist yet. Register a new participant first.");
            return;
        }

        System.out.println("\n---- Existing Participants ----");
        for (Participant p : participantMap.values()) {
            System.out.println(p.participantId + ". " + p.firstName + " " + p.surname);
        }

        int participantId = readInt(input, "Enter Participant ID: ");

        if (!participantMap.containsKey(participantId)) {
            System.out.println("Participant with ID " + participantId + " does not exist.");
            return;
        }

        registerParticipantForWorkshop(input, participantId);
    }

    static void registerParticipantForWorkshop(Scanner input, int participantId) {
        if (workshopList.isEmpty()){
            System.out.println("No Workshop has been registered. Create one first(manage workshops > b).");
            return;
        }

        System.out.println("\n---- Available Workshops ----");
        for (Workshop workshop : workshopList) {
            System.out.println(
                    workshop.workshopId + ". " + workshop.title +
                    "| Active: " + workshop.activeStatus +
                    "| Available Spaces: " + workshop.getAvailableSpaces());
        }

        int workshopId = readInt(input, "Enter Workshop ID to register for: ");

        Workshop workshop = workshopMap.get(workshopId);
        if (workshop == null) {
            System.out.println("Workshop with ID " + workshopId + " does not exist.");
            return;
        }

        if (!workshop.activeStatus) {
            System.out.println("Cannot register: \"" + workshop.title + "\" is not active.");
            return;
        }

        if (workshop.isFull()) {
            System.out.println("Cannot register: \"" + workshop.title + "\" is full.");
            return;
        }

        if (isAlreadyRegistered(participantId, workshopId)) {
            System.out.println("This participant is already registered for \"" + workshop.title + "\".");
            return;
        }

        participantWorkshopMap.computeIfAbsent(participantId, id -> new HashSet<>()).add(workshopId);

        workshop.numberOfRegistrations++;
        workshop.getAvailableSpaces(); // refreshes availableCapacity

        System.out.println("Linked participant " + participantId + " to workshop \"" + workshop.title + "\".");
        System.out.println("---------------------\n");
        hasUnsavedData = true;

        getRegisteredWorkshopTitles(participantId);

    }

    static boolean isAlreadyRegistered(int participantId, int workshopId) {
        Set<Integer> workshopsForParticipant = participantWorkshopMap.get(participantId);
        return workshopsForParticipant !=null && workshopsForParticipant.contains(workshopId);
    }

    static void displayAllPartcipants() {
        if (participantMap.isEmpty()) {
            System.out.println("No participants exist yet. Create one first(register for workshop).");
        }

        for (Participant p: participantMap.values()) {
            System.out.println(p.participantId + ". " + p.firstName + " " + p.surname);
        }

        System.out.println("\n--------------------------------------");
    }

    static String getRegisteredWorkshopTitles(int participantId) {
        Set<Integer> workshopIds = participantWorkshopMap.get(participantId);

        if (workshopIds == null || workshopIds.isEmpty()) {
            return "No workshops registered";
        }

        List<String> titles = new ArrayList<>();
        for (Integer workshopId : workshopIds) {
            Workshop workshop = workshopMap.get(workshopId);
            if (workshop != null) {
                titles.add(workshop.title);
            }
        }

        return String.join(", ", titles);
    }


    static void searchParticipant(Scanner input) {

        boolean foundParticipant = false;

        System.out.println("Search Participant by: " +
                "   a) Name" +
                "   b) ID");
        String choice = input.nextLine();
        if  (choice.equalsIgnoreCase("a")) {

            System.out.println("\n---- Enter Participant Name ----");
            String searchName = input.nextLine();

            for (Participant p : participantMap.values()) {
                if(nameMatches(p.firstName, searchName)) {
                    p.printSnapshot();
                    foundParticipant = true;
                }
            }

            if (!foundParticipant) {
                System.out.println("No participant matches \"" + searchName + "\".");
            }
        }

        if (choice.equalsIgnoreCase("b")) {

            int searchId = readInt(input, "\n---- Enter Participant ID Number ----");

            for (Participant p : participantMap.values()) {
                if (searchId == p.participantId) {
                    p.printSnapshot();
                    foundParticipant = true;
                }
            }

            if (!foundParticipant) {
                System.out.println("No participant matches \"" + searchId + "\".");
            }

        }

        System.out.println("\n------------------------------\n");

    }

    static void searchWorkshops(Scanner input) {
        System.out.println("Enter a workshop title(or part of it ) to search:");

        String searchTerm = input.nextLine().trim().toLowerCase();

        boolean foundWorkshop = false;
        for (String title : workshopTtles) {
            if (title.toLowerCase().contains(searchTerm)){
                System.out.println("Match found: "+ title);
                System.out.println("\n------------------------------\n");
                foundWorkshop = true;
            }
        }

        if (!foundWorkshop) {
            System.out.println("No such workshop matches "+ searchTerm+" .");
        }
    }

    static void filterWorkshopsByCategory(WorkshopCategory selected) {
        System.out.println("\n---- Workshops in category: " + selected + " ----");
        boolean found = false;

       for (Workshop w : workshopList) {
           if (w.category.equals(selected)) {
               System.out.println(w.workshopId + ". " + w.title);
           }
           found = true;
       }

        if (!found) {
            System.out.println("No workshops found in this category.");
        }

        System.out.println("----------------------------------------------\n");
    }

    static void filterWorkshopsWithAvailableSpaces() {
        System.out.println("\n---- Workshops With Available Spaces ----");
        boolean found = false;

        for (Workshop workshop : workshopList) {
            if (workshop.getAvailableSpaces() > 0) {
                System.out.println(workshop.workshopId + ". " + workshop.title +
                        " | Available Spaces: " + workshop.getAvailableSpaces());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No workshops currently have available spaces.");
        }

        System.out.println("----------------------------------------------\n");
    }

    static void calculateTotalExpectedIncome() {
        double totalIncome = 0;

        for (Workshop workshop : workshopList) {
            totalIncome += workshop.fee * workshop.numberOfRegistrations;
        }

        System.out.println("\nTotal expected workshop income: R" + totalIncome);

        System.out.println("----------------------------------------------\n");
    }

    static void calculateTotalRegistrations() {
        int totalRegistrations = 0;

        for (Workshop workshop : workshopList) {
            totalRegistrations += workshop.numberOfRegistrations;
        }

        System.out.println("\nTotal registrations across all workshops: " + totalRegistrations);

        System.out.println("----------------------------------------------\n");
    }

    static void countRegistrationsByWorkshop() {
        System.out.println("\n---- Registrations By Workshop ----");

        for (Workshop workshop : workshopList) {
            System.out.println(workshop.workshopId + ". " + workshop.title +
                    " -> " + workshop.numberOfRegistrations + " registration(s)");
        }

        System.out.println("----------------------------------------------\n");
    }

    static void writeRegistrationsToFile() {


        File participants = new File("src/data/participants.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(participants))){
            System.out.println("Attempting to save participants information.......");

            for  (Participant p : participantMap.values()) {

                bw.write(p.participantId+"|"+
                        p.firstName+"|"+
                        p.surname+"|"+
                        p.email+"|"+
                        p.telephoneNumber+"|"+
                        p.participantType+"|"+
                        p.activeStatus);
                bw.newLine();

            }

            System.out.println("Participant data saved successfully: data/participants.txt");

        } catch (IOException e) {
            System.out.println("Could not save participant information to file.");
        }

    }

    static void writeWorkshopsToFile() {

        File workshops = new File("src/data/workshops.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(workshops))) {
            System.out.println("Attempting to save workshops information.......");

            for (Workshop w : workshopList) {

                bw.write(w.workshopId+"|"+
                        w.title+"|"+
                        w.category+"|"+
                        w.facilitatorName+"|"+
                        w.workshopDate+"|"+
                        w.fee+"|"+
                        w.maximumCapacity+"|"+
                        w.numberOfRegistrations+"|"+
                        w.availableCapacity+"|"+
                        w.activeStatus);
                bw.newLine();

            }

            System.out.println("Workshop data saved successfully: data/workshops.txt");

        } catch (IOException e) {
            System.out.println("Could not save workshop information to file.");
        }
    }

    static void readParticipantsFromFile() {
        File participants = new File("src/data/participants.txt");

        if (!participants.exists()) {
            System.out.println("No saved participant file found.");
            return;
        }

        System.out.println("\n---- Participant Data from file ----");
        try (BufferedReader br = new BufferedReader(new FileReader(participants))){
            String line;
            int loadedCount = 0;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] fields = line.split("\\|");

                if (fields.length != 7){
                    System.out.println("skipped malformed line: " + line);
                    continue;
                }

                int participantId = Integer.parseInt(fields[0]);
                String firstName = fields[1];
                String surname = fields[2];
                String email = fields[3];
                String telephoneNumber = fields[4];
                String participantType = fields[5];
                String activeStatus = fields[6];

                Participant p = new Participant(
                        participantId,
                        firstName,
                        surname,
                        email,
                        telephoneNumber,
                        participantType,
                        activeStatus
                );

                participantMap.put(participantId, p);
                usedParticipantsIds.add(participantId);
                usedEmails.add(email);

                if (participantId > participantCount) {
                    participantCount = participantId;
                }

                loadedCount++;
            }

            System.out.println("Loaded " + loadedCount + " participants from file.");
        } catch (IOException e) {
            System.out.println("Could not read the participant data in the file");
        } catch (NumberFormatException e) {
            System.out.println("File has invalid or corrupted data and could be loaded");
        }
    }

    static void readWorkshopsFromFile() {
        File workshops = new File("src/data/workshops.txt");

        if (!workshops.exists()) {
            System.out.println("No saved workshop file found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(workshops))) {
            String line;
            int loadedCount = 0;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");

                if (fields.length != 9) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                int workshopId = Integer.parseInt(fields[0]);
                String title = fields[1];
                WorkshopCategory category = WorkshopCategory.valueOf(fields[2]);
                String facilitatorName = fields[3];
                LocalDate workshopDate = LocalDate.parse(fields[4]);
                double fee = Double.parseDouble(fields[5]);
                int maximumCapacity = Integer.parseInt(fields[6]);
                int numberOfRegistrations = Integer.parseInt(fields[7]);
                boolean activeStatus = Boolean.parseBoolean(fields[8]);

                Workshop w = new Workshop(
                        workshopId, title, category, facilitatorName, workshopDate,
                        fee, maximumCapacity, numberOfRegistrations, 0, activeStatus
                );
                w.getAvailableSpaces();

                workshopMap.put(workshopId, w);
                workshopList.add(w);
                usedWorkshopIds.add(workshopId);

                if (workshopId > workshopCount) {
                    workshopCount = workshopId;
                }

                loadedCount++;
            }

            System.out.println(loadedCount + " workshop(s) loaded from file.");

        } catch (IOException e) {
            System.out.println("Could not read workshop information from file.");
        } catch (Exception e) {
            System.out.println("File contains invalid data and could not be fully loaded.");
        }
    }

    static void exportRegistrationReport() {
        File csvFile = new File("src/reports/registration_report.csv");
        csvFile.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))){

            bw.write("Skills Workshop Management System - Registration Report");
            bw.newLine();
            bw.write("Generated: " + DateFormat.getDateTimeInstance().format(new Date()));
            bw.newLine();
            bw.newLine();

            bw.write("Participant ID,Participant Name,Workshop Title,Workshop Date,Amount Payable,Registration Status");
            bw.newLine();

            for(Map.Entry<Integer, Set<Integer>> entry : participantWorkshopMap.entrySet()) {
                Participant p = participantMap.get(entry.getKey());
                if (p == null) continue;

                for ( int workshopId : entry.getValue()) {
                    Workshop w = workshopMap.get(workshopId);
                    if (w == null ) continue;

                    bw.write(p.participantId+","+
                            p.firstName+" "+p.surname+","+
                            w.title+","+
                            w.workshopDate+","+
                            w.fee+","+
                            "Confirmed");

                    bw.newLine();

                }


            }
            System.out.println("Registration report created successfully: reports/registration_report.csv");



        } catch (IOException e) {
            System.out.println("Could not export registration report.");
        }


    }

    static void exportSummaryReport() {
        File reportFile = new File("src/reports/summary_report.csv");
        reportFile.getParentFile().mkdirs();

        int totalWorkshops = workshopList.size();
        int totalParticipants = participantMap.size();
        int totalRegistrations = 0;
        int totalAvailableSpaces = 0;
        double totalExpectedIncome = 0;

        for (Workshop w : workshopList) {
            totalRegistrations += w.numberOfRegistrations;
            totalAvailableSpaces += w.getAvailableSpaces();
            totalExpectedIncome += w.fee * w.numberOfRegistrations;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {

            bw.write("Skills Workshop Management System - Summary Report");
            bw.newLine();
            bw.write("Generated: " + DateFormat.getDateTimeInstance().format(new Date()));
            bw.newLine();
            bw.newLine();

            bw.write("Totals,Value");
            bw.newLine();
            bw.write("Total Workshops," + totalWorkshops);
            bw.newLine();
            bw.write("Total Participants," + totalParticipants);
            bw.newLine();
            bw.write("Total Registrations," + totalRegistrations);
            bw.newLine();
            bw.write("Total Available Spaces," + totalAvailableSpaces);
            bw.newLine();
            bw.write("Total Expected Income,R" + totalExpectedIncome);
            bw.newLine();

            System.out.println("Summary report created successfully: reports/summary_report.csv");

        } catch (IOException e) {
            System.out.println("Could not export summary report.");
        }
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

    static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("please enter a whole number.");
            }
        }
    }

    static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();

            try {
                return Double.parseDouble(line);
            }  catch (NumberFormatException e) {
                System.out.println("please enter a valid number(e.g 204.16).");
            }
        }
    }

    static <T> void displayList(List<T> items) {
        for (T item : items) {
            System.out.println(item.toString());
        }
    }
}




