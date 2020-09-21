import java.util.Scanner;

public class ParentMenu {
    static Scanner sc = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        Company zotato = new Company();

        zotato.addCustomer(new EliteCustomer(zotato, "Ram", "Pune"));
        zotato.addCustomer(new EliteCustomer(zotato,"Sam", "Delhi"));
        zotato.addCustomer(new SpecialCustomer(zotato, "Tim", "Gaya"));
        zotato.addCustomer(new Customer(zotato, "Kim", "Mumbai"));
        zotato.addCustomer(new Customer(zotato,"Jim", "Hyderabad"));

        zotato.addRestaurant(new AuthenticRestaurant("Shah's", "Chennai"));
        zotato.addRestaurant(new Restaurant("Ravi's", "Gaya"));
        zotato.addRestaurant(new AuthenticRestaurant("The Chinese", "Kangra"));
        zotato.addRestaurant(new FastFoodRestaurant("Wang's", "Mumbai"));
        zotato.addRestaurant(new Restaurant("Paradise", "Delhi"));

        while(true) {
            System.out.println("Welcome to Zotato \n" +
                    "  1) Enter as Restaurant Owner \n" +
                    "  2) Enter as Customer \n" +
                    "  3) Check User Details \n" +
                    "  4) Company Account Details \n" +
                    "  5) Exit");
            int opt = sc.nextInt();
            switch (opt) {
                case 1:
                    zotato.enterAsRestaurant();
                    break;
                case 2:
                    zotato.enterAsCustomer();
                    break;
                case 3:
                    zotato.showUserDetails();
                    break;
                case 4:
                    zotato.showAccountDetails();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Input!");
            }
        }
    }
}
