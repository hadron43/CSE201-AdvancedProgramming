import java.util.Vector;
import java.util.Scanner;

public class Restaurant extends User {
    private final Vector<Food> foodItems;
    protected int discount;
    private int noOfOrdersTaken;

    protected final Scanner sc = new Scanner(System.in);

    Restaurant(String name, String address) {
        super(name, address);
        foodItems = new Vector<>();
        discount = 0;
        noOfOrdersTaken = 0;
    }

    private void addFoodItem() {
        Food item = new Food(name);
        foodItems.add(item);
    }

    private void editItem() {
        if(foodItems.size() == 0) {
            System.out.println("No food items present! Please add some food items first.");
            return;
        }

        System.out.println();
        for (Food foodItem : foodItems) System.out.println(foodItem);

        int opt = sc.nextInt();
        for(Food food : foodItems) {
            if(food.getID() == opt) {
                food.modify();
                break;
            }
        }
    }

    protected void offerOnBillValue() {
        System.out.println("Offer on bill value - " + discount);
    }

    protected void orderSuccess() {
        ++noOfOrdersTaken;
    }

    public void showDetails() {
        System.out.println(this.toString() + ", "+ address + ", " +
                noOfOrdersTaken);
    }

    public Vector<Food> getFoodItems() {
        return foodItems;
    }

    public void enter() {
        while(true) {
            System.out.println("Welcome " + name + "\n" +
                    "  1) Add item \n" +
                    "  2) Edit item \n" +
                    "  3) Print Rewards \n" +
                    "  4) Discount on bill value \n" +
                    "  5) Exit");
            int opt = sc.nextInt();

            switch (opt) {
                case 1:
                    addFoodItem();
                    break;
                case 2:
                    editItem();
                    break;
                case 3:
                    System.out.println("Reward Points- "+getRewards());
                    break;
                case 4:
                    offerOnBillValue();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice! ");
            }
        }
    }
}
