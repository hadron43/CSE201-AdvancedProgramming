import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Patient {
    private final String name;
    private final int age;
    private final char tower;
    private final LocalDate reportDate, recoverDate;

    Patient(String name, int age, char tower, LocalDate reportDate){
        this.name = name;
        this.age = age;
        this.tower = tower;
        this.reportDate = reportDate;
        this.recoverDate = reportDate.plusDays(21);
    }

    String getName(){
        return this.name;
    }

    int getAge(){
        return this.age;
    }

    char getTower(){
        return this.tower;
    }

    String getReportDate(){
        return reportDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    String getRecoverDate(){
        return recoverDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    boolean recovered(LocalDate d) {
        if (recoverDate.isAfter(d))
            return false;
        return true;
    }

    boolean hasBeenInfected(LocalDate d){
        if (reportDate.isAfter(d))
            return false;
        return true;
    }
}
