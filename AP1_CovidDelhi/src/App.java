import java.util.*;

public class App {
    private final HashMap<Integer, Patient> patients;
    private final Vector<HealthCareInstitute> healthCareInstitutes;

    private int noOfPatientsInCamp, noOfOpenInstitutes;

    private static final Scanner scanner = new Scanner(System.in);

    App() {
        patients = new HashMap<>();
        healthCareInstitutes = new Vector<>();
        noOfOpenInstitutes = noOfPatientsInCamp = 0;
    }

    private HealthCareInstitute searchInstitute(String instituteName) {
        HealthCareInstitute institute = null;

        for(HealthCareInstitute h : healthCareInstitutes){
            if(h.getName().toUpperCase().equals(instituteName.toUpperCase())){
                institute = h;
                break;
            }
        }
        return institute;
    }

    private void addHealthCareInstitute (){
        String name;
        float maxTemp;
        int minOxygen, noOfAvailableBeds;

        name = scanner.next();
        System.out.print("Temperature Criteria - ");
        maxTemp = scanner.nextFloat();

        System.out.print("Oxygen Levels - ");
        minOxygen = scanner.nextInt();

        System.out.print("Number of Available beds - ");
        noOfAvailableBeds = scanner.nextInt();

        HealthCareInstitute newInstitute = new HealthCareInstitute(name,
                maxTemp, minOxygen, noOfAvailableBeds);

        newInstitute.displayDetails();

        Vector<Patient> freshlyAdmittedPatients = new Vector<>();
        for(Patient p : patients.values()) {
            if(newInstitute.getStatus() == Status.CLOSED)
                break;

            if(!p.isAdmitted() && newInstitute.addPatient(p, 1)) {
                freshlyAdmittedPatients.add(p);
            }
        }

        for(Patient p : patients.values()) {
            if(newInstitute.getStatus() == Status.CLOSED)
                break;

            if(!p.isAdmitted() && newInstitute.addPatient(p, 2)) {
                freshlyAdmittedPatients.add(p);
            }
        }

        for(Patient p : freshlyAdmittedPatients) {
            System.out.print("Recovery days for admitted patient ID " +
                    p.getId() + " - ");
            int advisedNoOfDays = scanner.nextInt();
            p.setAdvisedNoOfDays(advisedNoOfDays);
        }

        noOfPatientsInCamp -= freshlyAdmittedPatients.size();
        healthCareInstitutes.add(newInstitute);
        if(newInstitute.getStatus() == Status.OPEN)
            noOfOpenInstitutes ++;
    }

    private void removeAdmittedPatients() {
        System.out.println("Account ID removed of admitted patients");
        Iterator<HashMap.Entry<Integer, Patient>> iterator = patients.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Integer, Patient> entry = iterator.next();

            if(entry.getValue().isAdmitted()) {
                System.out.println(entry.getValue().getId());
                iterator.remove();
            }
        }
    }

    private void removeClosedInstitutes() {
        System.out.println("Accounts removed of Institute whose admission is closed");
        for(int i=0; i<healthCareInstitutes.size(); ++i) {
            if(healthCareInstitutes.get(i).getStatus() == Status.CLOSED){
                System.out.println(healthCareInstitutes.get(i).getName());
                healthCareInstitutes.remove(i);
                i--;
            }
        }
    }

    private void showPatientsInCamp() {
        System.out.println(noOfPatientsInCamp + " patients");
    }

    private void showNoOfOpenInstitutes() {
        System.out.println(noOfOpenInstitutes + " institutes are admitting patients currently");
    }

    private void displayInstitute(String instituteName) {
        HealthCareInstitute institute = searchInstitute(instituteName);

        if(institute != null)
            institute.displayDetails();
        else
            System.out.println("No such Health Care Institute found!");
    }

    private void displayPatientDetails(int patientId) {
        Patient patient = patients.get(patientId);
        if(patient != null)
            patient.displayDetails();
        else
            System.out.println("No such patient found!");
    }

    private void displayAllPatientsIdAndName() {
        for(Patient p : patients.values()){
            System.out.println(p.getId() + " " + p.getName());
        }
    }

    private void displayPatientsInInstitute (String instituteName) {
        HealthCareInstitute institute = searchInstitute(instituteName);

        if(institute != null)
            institute.displayPatientDetails();
        else
            System.out.println("No such Health Care Institute found!");
    }

    public static void main(String[] args) {
        App app = new App();
        app.noOfPatientsInCamp = scanner.nextInt();

        for(int i=0; i< app.noOfPatientsInCamp; ++i) {
            String name = scanner.next();
            float temperature = scanner.nextFloat();
            int oxygenLevels = scanner.nextInt(), age = scanner.nextInt();
            Patient newPatient = new Patient(name, temperature, oxygenLevels, age);
            app.patients.put(newPatient.getId(), newPatient);
        }

        while(app.noOfPatientsInCamp > 0) {
            int opt = scanner.nextInt();
            switch (opt) {
                case 1:
                    app.addHealthCareInstitute();
                    break;
                case 2:
                    app.removeAdmittedPatients();
                    break;
                case 3:
                    app.removeClosedInstitutes();
                    break;
                case 4:
                    app.showPatientsInCamp();
                    break;
                case 5:
                    app.showNoOfOpenInstitutes();
                    break;
                case 6:
                    app.displayInstitute(scanner.next());
                    break;
                case 7:
                    app.displayPatientDetails(scanner.nextInt());
                    break;
                case 8:
                    app.displayAllPatientsIdAndName();
                    break;
                case 9:
                    app.displayPatientsInInstitute(scanner.next());
                    break;
                default:
                    System.out.println("Invalid Input! Enter between 1-9.");
            }
        }

        System.out.println("\nAll patients have been admitted. Program Complete...");
    }
}
