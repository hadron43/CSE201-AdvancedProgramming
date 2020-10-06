package Users;

import java.util.ArrayList;

public abstract class Player {
    private final int id;
    private int HP;
    private boolean active;

    private static int count = 0;

    public Player(int HP) {
        id = ++count;
        this.HP = HP;
        active = true;
    }

    public static void resetCounter() { count = 0; }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Player))
            return false;

        Player player = (Player)o;
        return id == player.id;
    }

    public Player vote(ArrayList<Player> players) {
        ArrayList<Player> candidates = new ArrayList<>();
        for(Player p : players) {
            if(p.isAlive())
                candidates.add(p);
        }
        int n = (int)(Math.random()*100000)%candidates.size();
        return candidates.get(n);
    }

    public int getHP() {
        return HP;
    }

    public int getId() { return this.id; }

    public boolean isAlive() {
        return active;
    }

    public void kill() { active = false; }

    protected void reduceHP(int byAmount) {
        HP = Math.max(0, HP - byAmount);
    }

    public void heal(int HP) { this.HP += HP; }

    @Override
    public String toString() { return "Player"+this.id; }
}
