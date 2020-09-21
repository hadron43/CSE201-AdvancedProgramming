import java.util.HashMap;
import java.util.Scanner;

public class Restaurant extends User {
    private final HashMap<Integer, Food> foodItems;
    protected int discount;
    private int noOfOrdersTaken;

    protected final Scanner sc = new Scanner(System.in);

    Restaurant(String name, String address) {
        super(name, address);
        foodItems = new HashMap<>();
        discount = 0;
        noOfOrdersTaken = 0;
    }

    private void addFoodItem() {
        Food item = new Food(getName());
        foodItems.put(item.getID(), item);
    }

    private void editItem() {
        if(foodItems.size() == 0) {
            System.out.println("No food items present! Please add some food items first.");
            return;
        }

        for (Food foodItem : foodItems.values()) System.out.println(foodItem);

        int opt = sc.nextInt();
        foodItems.get(opt).modify();
    }

    protected void offerOnBillValue() {
        System.out.println("Offer on bill value - " + discount);
    }

    protected void orderSuccess(HashMap<Integer, Integer> boughtItems) {
        ++noOfOrdersTaken;

        for(int i : boughtItems.keySet()) {
            foodItems.get(i).reduceQuantity(boughtItems.get(i));
        }
    }

    public void showDetails() {
        System.out.println(this.toString() + ", "+ getAddress() + ", " +
                noOfOrdersTaken);
    }

    public void displayFoodItems() {
        for(Food f : foodItems.values())
            System.out.println(f);
    }

    protected Food getFoodById(int id) {
        return foodItems.get(id);
    }

    public void enter() {
        while(true) {
            System.out.println("Welcome " + getName() + "\n" +
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
                    System.out.println("Reward Points - "+getRewards());
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
