import java.util.Scanner;
import java.util.Vector;

public class Customer extends User {
    private float walletMoney;
    protected int deliveryCharge;

    private Cart cart;
    private final Vector<Cart> previousOrders;

    private final Company company;
    private final Scanner sc = ParentMenu.sc;

    Customer(Company _company, String name, String address) {
        super(name, address);
        deliveryCharge = 40;
        walletMoney = 1000;
        cart = null;
        previousOrders = new Vector<>();
        this.company = _company;
    }

    private float spendRewards(float value) {
        float spent = Math.min(value, rewards);
        rewards -= spent;
        value -= spent;
        return value;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public boolean confirmOrder(Cart c, float orderValue, Object callerObject) {
        if(!(callerObject instanceof Company)){
            System.out.println("Only company is allowed to confirm order");
            return false;
        }

        System.out.println("Items in cart");
        System.out.print(c);
        System.out.println("Delivery Charge: " + deliveryCharge + "\n" +
                "Total Order Value - INR " + (orderValue+deliveryCharge) + "/-");
        if(orderValue > walletMoney + rewards) {
            System.out.println("Insufficient Balance! \n" +
                    "Delete Some Item. Enter code to delete: ");
            int code = sc.nextInt();
            cart.removeItem(code);
            return false;
        }
        System.out.println("  1) Proceed to checkout");
        int opt = sc.nextInt();
        if(opt != 1)
            return false;
        previousOrders.insertElementAt(c, 0);
        if(previousOrders.size()>10) {
            previousOrders.remove(10);
        }

        float temp = orderValue;
        temp = spendRewards(temp);
        walletMoney -= temp;
        cart = null;

        System.out.println(c.countItems() + " items successfully bought for INR "
                +(orderValue + deliveryCharge) + "/-");

        return true;
    }

    private void displayOrders() {
        for(Cart c : previousOrders) {
            c.displayBought();
        }
    }

    public void showDetails() {
        System.out.println(toString() + ", "+address+", "+walletMoney);
    }

    public void enterMenu(Vector<Restaurant> restaurants) {
        while(true) {
            System.out.println("Welcome " + name + "\n" +
                    "Customer Menu \n" +
                    ((cart == null) ? "1) Select Restaurant \n" : "1) Search Item\n") +
                    "2) Checkout Cart \n" +
                    "3) Rewards won \n" +
                    "4) Print the Recent Orders \n" +
                    "5) Exit");
            int opt = sc.nextInt();

            switch (opt) {
                case 1:
                    if(cart == null) {
                        System.out.println("Choose Restaurant");
                        for(int i=0; i<restaurants.size(); ++i)
                            System.out.println("  "+(i+1) + ") " + restaurants.get(i));
                        int rCode = sc.nextInt();
                        cart = new Cart(this, restaurants.get(rCode-1));
                    }
                    Vector<Food> foods = cart.getRestaurant().getFoodItems();
                    System.out.println("Choose item by code");
                    for(Food f : foods)
                        System.out.println(f);
                    int fCode = sc.nextInt();
                    System.out.println("Enter item quantity - ");
                    int quantity = sc.nextInt();

                    Food selectedFood = null;
                    for(Food f : foods)
                        if(f.getID() == fCode)
                            selectedFood = f;

                    if(selectedFood == null)
                        break;

                    if(selectedFood.getQuantity() >= quantity)
                        cart.addToCart(selectedFood, quantity);
                    else
                        System.out.println("Insufficient Quantity!");

                    break;
                case 2:
                    company.processOrder(cart, cart.getRestaurant(), this);
                    break;
                case 3:
                    System.out.println("Total Rewards - "+getRewards());
                    break;
                case 4:
                    displayOrders();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
