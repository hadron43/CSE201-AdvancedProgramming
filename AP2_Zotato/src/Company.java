import java.util.Scanner;
import java.util.Vector;

public final class Company {
    private final Vector<Restaurant> restaurants;
    private final Vector<Customer> customers;
    private double transactionFeeCollected;
    private long deliveryChargesCollected;

    private final Scanner sc = ParentMenu.sc;

    Company() {
        restaurants = new Vector<>();
        customers = new Vector<>();
    }

    private int calcRewards(Restaurant r, float orderValue) {
        if(r instanceof FastFoodRestaurant)
            return 10*(int)(orderValue/150);
        if(r instanceof AuthenticRestaurant)
            return 25*(int)(orderValue/200);
        return 5*(int)(orderValue/100);
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void processOrder(Cart cart, Restaurant restaurant, Object callerObject) {
        if(!(callerObject instanceof Customer)) {
            System.out.println("Invalid Request! Only customers can request orders.");
            return;
        }

        Customer customer = (Customer) callerObject;
        float orderValue = cart.calcValue();
        if(restaurant instanceof Discountable) {
            Discountable res = (Discountable) restaurant;
            orderValue = res.calcDiscount(orderValue);
        }

        if(customer instanceof Discountable) {
            Discountable cus = (Discountable) customer;
            orderValue = cus.calcDiscount(orderValue);
        }

        if(!customer.confirmOrder(cart, orderValue, this))
            return;

        restaurant.orderSuccess(cart.getItemList());
        transactionFeeCollected += 0.01 * orderValue;
        deliveryChargesCollected += customer.getDeliveryCharge();

        int rewards = calcRewards(restaurant, orderValue);
        restaurant.addRewards(rewards, this);
        customer.addRewards(rewards, this);
    }

    public void enterAsRestaurant() {
        System.out.println("Choose restaurant");
        for(int i=0; i<restaurants.size(); ++i) {
            System.out.println("  "+(i+1)+") "+restaurants.get(i));
        }
        int opt = sc.nextInt();
        restaurants.get(opt-1).enter();
    }

    public void enterAsCustomer() {
        for(int i=0; i<customers.size(); ++i) {
            System.out.println("  "+(i+1)+") "+customers.get(i));
        }
        int opt = sc.nextInt();
        customers.get(opt-1).enterMenu(restaurants);
    }

    public void showAccountDetails() {
        System.out.println("Zotato Account Details \n" +
                "Total Company balance - INR " + transactionFeeCollected + "/-\n" +
                "Total Delivery Charges Collected - INR " + deliveryChargesCollected + "/-");
    }

    public void showUserDetails() {
        System.out.println("  1) Customer List \n" +
                "  2) Restaurant List");
        int opt = sc.nextInt();
        if(opt == 1) {
            for (User user : customers)
                System.out.println("  "+opt++ + ". " +user.getName());

            opt = sc.nextInt();
            customers.get(opt-1).showDetails();
        }
        else {
            opt = 1;
            for (User user : restaurants)
                System.out.println("  "+opt++ + ". " +user.getName());

            opt = sc.nextInt();
            restaurants.get(opt-1).showDetails();
        }
    }
}
