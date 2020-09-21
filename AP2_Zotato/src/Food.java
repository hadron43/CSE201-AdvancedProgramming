import java.util.Scanner;

public final class Food implements Discountable {
    private final int ID;
    private int quantity;
    private String name, category;
    private float price;
    private int discount;

    private static int counter = 0;
    private final  String restaurantName;

    private final Scanner sc = ParentMenu.sc;

    Food(String _restaurantName) {
        this.ID = ++counter;

        System.out.println("Enter food item details");
        System.out.println("Food name: ");
        name = sc.next();
        System.out.println("Item price: ");
        price = sc.nextFloat();
        System.out.println("Item quantity: ");
        quantity = sc.nextInt();
        System.out.println("Item Category: ");
        category = sc.next();
        System.out.println("Offer: ");
        discount = sc.nextInt();

        System.out.println(ID+" "+name+" "+price+" "+quantity+" "+discount+
                "% off "+category);

        this.restaurantName = _restaurantName;
    }

    @Override
    public float calcDiscount(float value) {
        //Returns discounted value
        return value - value*((float)discount)/100;
    }

    @Override
    public String toString() {
        return ID + " " + restaurantName + " - " + name + " " + price
                + " " + quantity + " " + discount + "% off " + category;
    }

    public void modify() {
        System.out.println("Choose an attribute to edit: \n" +
                "  1) Name \n" +
                "  2) Price \n" +
                "  3) Quantity \n" +
                "  4) Category \n" +
                "  5) Offer ");
        int opt = sc.nextInt();
        switch (opt) {
            case 1:
                System.out.println("Enter new name : ");
                name = sc.next();
                break;
            case 2:
                System.out.println("Enter new price : ");
                price = sc.nextFloat();
                break;
            case 3:
                System.out.println("Enter new quantity : ");
                quantity = sc.nextInt();
                break;
            case 4:
                System.out.println("Enter new category : ");
                category = sc.next();
                break;
            case 5:
                System.out.println("Enter new offer : ");
                discount = sc.nextInt();
                break;
            default:
                System.out.println("Invalid Choice!");
        }
        System.out.println(this.toString());
    }

    public int getID() { return  this.ID; }
    public String getName() { return this.name; }
    public int getQuantity() { return  this.quantity; }
    public float getPrice() { return this.price; }
}
