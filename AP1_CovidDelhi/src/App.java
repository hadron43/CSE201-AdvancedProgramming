import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int noOfPatients = scanner.nextInt();
        Camp camp = new Camp(noOfPatients);

        for(int i = 0; i< noOfPatients; ++i) {
            String name = scanner.next();
            float temperature = scanner.nextFloat();
            int oxygenLevels = scanner.nextInt(), age = scanner.nextInt();
            Patient newPatient = new Patient(name, temperature, oxygenLevels, age);
            camp.addPatient(newPatient);
        }

        while(camp.getNoOfPatientsInCamp() > 0) {
            int opt = scanner.nextInt();
            switch (opt) {
                case 1:
                    camp.addHealthCareInstitute();
                    break;
                case 2:
                    camp.removeAdmittedPatients();
                    break;
                case 3:
                    camp.removeClosedInstitutes();
                    break;
                case 4:
                    camp.showPatientsInCamp();
                    break;
                case 5:
                    camp.showNoOfOpenInstitutes();
                    break;
                case 6:
                    camp.displayInstitute(scanner.next());
                    break;
                case 7:
                    camp.displayPatientDetails(scanner.nextInt());
                    break;
                case 8:
                    camp.displayAllPatientsIdAndName();
                    break;
                case 9:
                    camp.displayPatientsInInstitute(scanner.next());
                    break;
                default:
                    System.out.println("Invalid Input! Enter between 1-9.");
            }
        }

        System.out.println("\nAll patients have been admitted. Program Complete...");
    }
}
