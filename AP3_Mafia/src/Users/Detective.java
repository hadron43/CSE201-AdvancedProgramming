package Users;

import java.util.ArrayList;

public final class Detective extends Player {

    public Detective() {
        super(800);
    }

    public Player voteCheck(ArrayList<Player> players) {
        ArrayList<Player> candidates = new ArrayList<>();
        for(Player player : players) {
            if(!(player instanceof Detective) && player.isAlive()) {
                candidates.add(player);
            }
        }

        int n = (int)(Math.random()*100000)%candidates.size();
        return candidates.get(n);
    }
}
