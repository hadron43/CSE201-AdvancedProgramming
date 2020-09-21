public final class SpecialCustomer extends Customer implements Discountable {
    SpecialCustomer(Company company, String name, String address) {
        super(company, name, address);
        deliveryCharge = 20;
    }

    @Override
    public float calcDiscount(float billValue) {
        if(billValue > 200)
            return billValue - 25;
        return billValue;
    }

    @Override
    public String toString() {
        return name+" (Special) ";
    }
}
