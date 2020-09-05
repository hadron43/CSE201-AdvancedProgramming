import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        Patient[] patients = new Patient[20];
        patients[0] = new Patient("Flora", 6, 'A', LocalDate.of(2020, 4, 1));
        patients[1] = new Patient("Denys", 24, 'B', LocalDate.of(2020, 4, 1));
        patients[2] = new Patient("Jim", 42, 'C', LocalDate.of(2020, 5, 18));
        patients[3] = new Patient("Hazel", 6, 'D', LocalDate.of(2020, 6, 23));
        patients[4] = new Patient("Caery", 72, 'A', LocalDate.of(2020, 6, 1));
        patients[5] = new Patient("David", 7, 'B', LocalDate.of(2020, 6, 14));
        patients[6] = new Patient("Kevim", 37, 'D', LocalDate.of(2020, 6, 5));
        patients[7] = new Patient("Tom", 67, 'D', LocalDate.of(2020, 6, 20));
        patients[8] = new Patient("Bob", 74, 'A', LocalDate.of(2020, 7, 4));
        patients[9] = new Patient("Rachel", 48, 'C', LocalDate.of(2020, 7, 24));
        patients[10] = new Patient("Thomas", 21, 'C', LocalDate.of(2020, 6, 11));
        patients[11] = new Patient("Mary", 17, 'D', LocalDate.of(2020, 6, 21));
        patients[12] = new Patient("Smith", 89, 'A', LocalDate.of(2020, 8, 7));
        patients[13] = new Patient("Pearson", 47, 'B', LocalDate.of(2020, 6, 4));
        patients[14] = new Patient("Anderson", 62, 'B', LocalDate.of(2020, 7, 27));
        patients[15] = new Patient("Johnson", 10, 'D', LocalDate.of(2020, 8, 1));
        patients[16] = new Patient("Robertz", 50, 'A', LocalDate.of(2020, 8, 9));
        patients[17] = new Patient("Julie", 86, 'B', LocalDate.of(2020, 5, 2));
        patients[18] = new Patient("Edith", 42, 'D', LocalDate.of(2020, 6, 7));
        patients[19] = new Patient("John", 95, 'D', LocalDate.of(2020, 6, 1));

        Window win = new Window(patients);
    }
}
