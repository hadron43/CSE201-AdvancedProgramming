public final class FastFoodRestaurant extends Restaurant implements Discountable {
    FastFoodRestaurant(String name, String address) {
        super(name, address);
    }

    @Override
    protected void offerOnBillValue() {
        System.out.print("Enter discount on bill value - ");
        discount = sc.nextInt();
    }

    @Override
    public String toString() {
        return name + " (Fast Food)";
    }

    @Override
    public float calcDiscount(float orderValue) {
        return orderValue - orderValue * ((float)discount)/100;
    }
}
