import java.util.*;

public class Camp {
    private final HashMap<Integer, Patient> patients;
    private final Vector<HealthCareInstitute> healthCareInstitutes;

    private int noOfPatientsInCamp, noOfOpenInstitutes;

    Camp(int noOfPatients) {
        patients = new HashMap<>();
        healthCareInstitutes = new Vector<>();
        noOfOpenInstitutes = noOfPatientsInCamp = 0;

        this.noOfPatientsInCamp = noOfPatients;
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

    public int getNoOfPatientsInCamp() {
        return noOfPatientsInCamp;
    }

    public void addPatient(Patient patient) {
        patients.put(patient.getId(), patient);
    }

    public void addHealthCareInstitute (){
        String name;
        float maxTemp;
        int minOxygen, noOfAvailableBeds;

        Scanner scanner = new Scanner(System.in);

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

    public void removeAdmittedPatients() {
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

    public void removeClosedInstitutes() {
        System.out.println("Accounts removed of Institute whose admission is closed");
        for(int i=0; i<healthCareInstitutes.size(); ++i) {
            if(healthCareInstitutes.get(i).getStatus() == Status.CLOSED){
                System.out.println(healthCareInstitutes.get(i).getName());
                healthCareInstitutes.remove(i);
                i--;
            }
        }
    }

    public void showPatientsInCamp() {
        System.out.println(noOfPatientsInCamp + " patients");
    }

    public void showNoOfOpenInstitutes() {
        System.out.println(noOfOpenInstitutes + " institutes are admitting patients currently");
    }

    public void displayInstitute(String instituteName) {
        HealthCareInstitute institute = searchInstitute(instituteName);

        if(institute != null)
            institute.displayDetails();
        else
            System.out.println("No such Health Care Institute found!");
    }

    public void displayPatientDetails(int patientId) {
        Patient patient = patients.get(patientId);
        if(patient != null)
            patient.displayDetails();
        else
            System.out.println("No such patient found!");
    }

    public void displayAllPatientsIdAndName() {
        for(Patient p : patients.values()){
            System.out.println(p.getId() + " " + p.getName());
        }
    }

    public void displayPatientsInInstitute (String instituteName) {
        HealthCareInstitute institute = searchInstitute(instituteName);

        if(institute != null)
            institute.displayPatientDetails();
        else
            System.out.println("No such Health Care Institute found!");
    }
}
