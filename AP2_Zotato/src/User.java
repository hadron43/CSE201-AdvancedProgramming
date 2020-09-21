public class User {
    protected String name, address;
    protected int rewards;

    User(String _name, String _address) {
        this.name = _name;
        this.address = _address;
        rewards = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getRewards() { return rewards; }

    public String getName() { return name; }

    protected void addRewards(int _rewards, Object caller) {
        if(!(caller instanceof Company)) {
            System.out.println("Only company is allowed to reward a User!");
            return;
        }
        rewards += _rewards;
    }
}
