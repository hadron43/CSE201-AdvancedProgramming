public final class EliteCustomer extends Customer implements Discountable {
    EliteCustomer(Company company, String name, String address) {
        super(company, name, address);
        deliveryCharge = 0;
    }

    @Override
    public float calcDiscount(float billValue) {
        if(billValue > 200)
            return billValue - 50;
        return billValue;
    }

    @Override
    public String toString() {
        return getName()+" (Elite) ";
    }
}
