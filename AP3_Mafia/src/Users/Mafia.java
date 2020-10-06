package Users;

import java.util.ArrayList;

public final class Mafia extends Player {

    public Mafia() {
        super(2500);
    }

    public Player voteKill(ArrayList<Player> players) {
        ArrayList<Player> candidates = new ArrayList<>();
        for(Player player : players) {
            if(!(player instanceof Mafia) && player.isAlive()) {
                candidates.add(player);
            }
        }

        int n = (int)(Math.random()*100000)%candidates.size();
        return candidates.get(n);
    }

    public void attackPlayer(Player p, int noOfMafias) {
        int damage = p.getHP()/noOfMafias;
        if(getHP() < p.getHP()/noOfMafias)
            damage = getHP();
        p.reduceHP(damage);
        this.reduceHP(damage);
    }
}
