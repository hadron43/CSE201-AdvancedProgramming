import java.util.Vector;

enum Status {
    OPEN,
    CLOSED
}

public class HealthCareInstitute {
    private Status status;
    private final String name;
    private final int minOxygenLevels;
    private final float maxTemperature;
    private int availableBeds;

    private final Vector<Patient> patients;

    private void setStatus(){
        if(availableBeds > 0)
            status = Status.OPEN;
        else
            status = Status.CLOSED;
    }

    HealthCareInstitute(String name, float maxTemperature,
                        int minOxygenLevels, int availableBeds) {
        this.name = name;
        this.maxTemperature = maxTemperature;
        this.minOxygenLevels = minOxygenLevels;
        this.availableBeds = availableBeds;

        this.setStatus();
        this.patients = new Vector<>();
    }

    public boolean addPatient(Patient p, int mode){
        boolean success = false;
        if(status == Status.OPEN){
            if( (1 == mode && p.getOxygenLevels() >= minOxygenLevels) ||
                    (2 == mode && p.getTemperature() <= maxTemperature) ){
                patients.add(p);
                p.admit(this.getName());
                success = true;
                availableBeds--;
                this.setStatus();
            }
        }
        return success;
    }

    public void displayDetails() {
        System.out.println(name + "\n" +
            "Temperature should be <= " + maxTemperature + "\n" +
            "Oxygen levels should be >= " + minOxygenLevels + "\n" +
            "Number of Available beds - " + availableBeds + "\n" +
            "Admission Status - " + ((status == Status.OPEN)?"OPEN":"CLOSED"));
    }

    public void displayPatientDetails() {
        for(Patient p : patients){
            System.out.println(p.getName() + ", recovery time is " +
                    p.getAdvisedNoOfDays() + " days");
        }
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
