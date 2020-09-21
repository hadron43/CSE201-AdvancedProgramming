import java.util.HashMap;

public final class Cart {
    private final HashMap<Food, Integer> items;
    private final Customer customer;
    private final Restaurant restaurant;

    Cart(Customer _customer, Restaurant _restaurant) {
        this.customer = _customer;
        this.restaurant = _restaurant;
        this.items = new HashMap<>();
    }

    public void addToCart(Food food, int quantity) {
        System.out.println("Items added to cart");
        if(items.containsKey(food))
            items.replace(food, items.get(food) + quantity);
        else
            items.put(food, quantity);
    }

    public int countItems() {
        int ans = 0;
        for(Food f : items.keySet()) {
            ans += items.get(f);
        }
        return ans;
    }

    public void removeItem(int id) {
        for(Food f : items.keySet()) {
            if(f.getID() == id) {
                items.remove(f);
                return;
            }
        }
    }

    public void displayBought() {
        System.out.print("Bought item ");
        for(Food f : items.keySet())
            System.out.print(f.getName() + ", Quantity: " + items.get(f)
                    + " for Rs " + f.calcDiscount(f.getPrice()*items.get(f)));
        System.out.println(" from " + restaurant + " and Delivery Charge: " +
                customer.getDeliveryCharge());
    }

    @Override
    public String toString() {
        String ans = "";
        for(Food f : items.keySet())
            ans += f.toString() + "\n";
        return ans;
    }

    public float calcValue() {
        float ans = 0;
        for(Food f : items.keySet()) {
            ans += f.calcDiscount(f.getPrice()*items.get(f));
        }
        return ans;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
    public HashMap<Food, Integer> getItems() { return items; }
}
