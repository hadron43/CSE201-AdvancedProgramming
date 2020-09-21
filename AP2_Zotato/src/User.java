public class User {
    private String name, address;
    private int rewards;

    User(String _name, String _address) {
        this.name = _name;
        this.address = _address;
        rewards = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    protected int getRewards() { return rewards; }

    protected String getName() { return name; }

    protected String getAddress() { return  address; }

    protected void addRewards(int _rewards, Object caller) {
        if(!(caller instanceof Company)) {
            System.out.println("Only company is allowed to reward a User!");
            return;
        }
        rewards += _rewards;
    }

    protected void updateRewards(int _rewards, Object callerObject) {
        if(!(callerObject instanceof Customer))
            return;
        rewards = _rewards;
    }
}
