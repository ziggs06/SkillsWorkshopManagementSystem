class Participant {

    int participantId;
    String firstName;
    String surname;
    String email;
    String telephoneNumber;
    String participantType;
    String activeStatus;

    Participant(int participantId,
                String firstName,
                String surname,
                String email,
                String telephoneNumber,
                String participantType,
                String activeStatus) {

        this.participantId = participantId;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.participantType = participantType;
        this.activeStatus = activeStatus;
    }

    void printSnapshot() {
        System.out.println("\n---- Participant Snapshot ----");
        System.out.println("Participant ID     : " + participantId);
        System.out.println("Name               : " + firstName);
        System.out.println("Surname            : " + surname);
        System.out.println("Email              : " + email);
        System.out.println("Telephone Number   : " + telephoneNumber);
        System.out.println("Participant Type   : " + participantType);
        System.out.println("Status             : " + activeStatus);
    }
}
