import java.time.LocalDate;

class Workshop {

    int workshopId;
    String title;
    WorkshopApplication.WorkshopCategory category;
    String facilitatorName;
    LocalDate workshopDate;
    double fee;
    int maximumCapacity;
    int numberOfRegistrations;
    int availableCapacity;
    boolean activeStatus;


    Workshop(int workshopId,
             String title,
             WorkshopApplication.WorkshopCategory category,
             String facilitatorName,
             LocalDate workshopDate,
             double fee,
             int maximumCapacity,
             int numberOfRegistrations,
             int availableCapacity,
             boolean activeStatus) {
        this.workshopId = workshopId;
        this.title = title;
        this.category = category;
        this.facilitatorName = facilitatorName;
        this.workshopDate = workshopDate;
        this.fee = fee;
        this.maximumCapacity = maximumCapacity;
        this.numberOfRegistrations = numberOfRegistrations;
        this.availableCapacity = availableCapacity;
        this.activeStatus = activeStatus;
    }

    int getAvailableSpaces() {
        availableCapacity = maximumCapacity - numberOfRegistrations;
        return availableCapacity;
    }

    boolean isFull() {
        return getAvailableSpaces() <= 0;
    }

    void printSnapshot() {
        System.out.println("\n---- Workshop Snapshot ----");
        System.out.println("Workshop ID        : " + workshopId);
        System.out.println("Title              : " + title);
        System.out.println("Category           : " + category);
        System.out.println("Facilitator        : " + facilitatorName);
        System.out.println("Date               : " + workshopDate);
        System.out.println("Fee                : R" + fee);
        System.out.println("Capacity           : " + maximumCapacity);
        System.out.println("Registrations      : " + numberOfRegistrations);
        System.out.println("Available Spaces   : " + availableCapacity);
        System.out.println("Max Standard Slots : " + WorkshopApplication.MAX_STANDARD_WORKSHOPS);
    }
}