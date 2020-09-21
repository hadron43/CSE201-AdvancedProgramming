public final class AuthenticRestaurant extends Restaurant implements Discountable {
    AuthenticRestaurant(String name, String address) {
        super(name, address);
    }

    @Override
    protected void offerOnBillValue() {
        System.out.print("Enter discount on bill value - ");
        discount = sc.nextInt();
    }

    @Override
    public String toString() {
        return getName() + " (Authentic)";
    }

    @Override
    public float calcDiscount(float orderValue) {
        float ans = orderValue - orderValue * ((float)discount)/100;
        if(ans > 100)
            ans -= 50;
        return ans;
    }
}
