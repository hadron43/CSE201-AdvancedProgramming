public class Patient {
    private final String name;
    private final int age, oxygenLevels, id;
    private final float temperature;
    private int advisedNoOfDays;

    private static int counter = 0;

    private String hospital;

    Patient(String name, float temperature, int oxygenLevels, int age){
        this.name = name;
        this.temperature = temperature;
        this.oxygenLevels = oxygenLevels;
        this.age = age;
        this.advisedNoOfDays = -1;

        this.id = ++counter;
        hospital = null;
    }

    public void displayDetails() {
        System.out.println(name + "\n" +
                "Temperature is " + temperature + "\n" +
                "Oxygen levels is " + oxygenLevels
            );

        if(hospital == null)
            System.out.println(
                "Admission Status - " + "Not Admitted"
            );
        else
            System.out.println(
                "Admission Status - " + "Admitted" + "\n" +
                "Admitting Institute - " + hospital
            );
    }

    public void admit(String hospital){
        this.hospital = hospital;
    }

    public boolean isAdmitted(){
        return hospital != null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOxygenLevels() {
        return oxygenLevels;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getAdvisedNoOfDays() {
        return advisedNoOfDays;
    }

    public void setAdvisedNoOfDays(int advisedNoOfDays) {
        this.advisedNoOfDays = advisedNoOfDays;
    }
}
