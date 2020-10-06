package Users;

import java.util.ArrayList;

public final class Healer extends Player {
    public Healer() {
        super(800);
    }

    public Player voteHeal(ArrayList<Player> players) {
        ArrayList<Player> candidates = new ArrayList<>();
        for(Player player : players) {
            if(player.isAlive())
                candidates.add(player);
        }
        int n = (int)(Math.random()*100000)%candidates.size();
        return candidates.get(n);
    }
}
