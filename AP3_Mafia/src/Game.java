import Exceptions.*;
import Users.*;

import java.util.*;

class Vote implements Comparable {
    private final int id;
    private int vote;
    Vote(int id, int vote) {
        this.id = id;
        this.vote = vote;
    }

    public int getId() { return id; }
    public int getVote() { return vote; }

    public void increaseVote() { vote++; }

    @Override
    public int compareTo(Object o) {
        if(! (o instanceof Vote))
            return -1;
        Vote v = (Vote) o;
        return vote - v.vote;
    }
}

public final class Game {
    private final ArrayList<Player> players;
    private final ArrayList<Player> inactivePlayers;
    private Player user;

    private final Scanner sc = Main.sc;

    Game(int N) throws NotEnoughPlayersException {
        if(N < 6)
            throw new NotEnoughPlayersException();

        Player.resetCounter();
        players = new ArrayList<>(N);
        inactivePlayers = new ArrayList<>(N);

        boolean inputTaken = false;
        int inp = 0;
        while(!inputTaken) {
            try {
                System.out.println("Choose a character\n" +
                        "1) Mafia\n" +
                        "2) Detective\n" +
                        "3) Healer\n" +
                        "4) Commoner\n" +
                        "5) Assign Randomly");
                inp = sc.nextInt();
                if(inp < 1 || inp >5)
                    throw new NullPointerException("Input out of bounds!!");
                inputTaken = true;
            } catch(InputMismatchException e) {
                System.out.println("NaN: Invalid Input!");
                sc.next();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        int nMafia = N/5, nDetective = N/5, nHealer = Math.max(1, N/10);
        int nCommoner = N - nMafia - nDetective - nHealer;

        if(inp == 5)
            inp = (int)(Math.random()*10)%4 + 1;

        while(nMafia>0 || nDetective>0 || nCommoner>0 || nHealer>0) {
            int n = (int)(Math.random()*100)%4;
            switch (n) {
                case 0:
                    if(nMafia > 0) {
                        players.add(new Mafia());
                        nMafia--;
                        break;
                    }
                case 1:
                    if(nDetective > 0) {
                        players.add(new Detective());
                        nDetective--;
                        break;
                    }
                case 2:
                    if(nHealer > 0) {
                        players.add(new Healer());
                        nHealer--;
                        break;
                    }
                case 3:
                    if(nCommoner > 0) {
                        players.add(new Commoner());
                        nCommoner--;
                        break;
                    }
            }
        }

        user = null;
        for (Player player : players) {
            if (inp == 1 && player instanceof Mafia)
                user = player;
            else if (inp == 2 && player instanceof Detective)
                user = player;
            else if (inp == 3 && player instanceof Healer)
                user = player;
            else if (inp == 4 && player instanceof Commoner)
                user = player;
        }

        System.out.println("You are a "+user.getClass().getName().replaceFirst("Users.", ""));
        System.out.println("You are "+user);
        if(user instanceof Mafia) {
            System.out.print("Other mafias are: ");
            for(Player p : players) {
                if(p instanceof Mafia && !p.equals(user))
                    System.out.print("["+p+"] ");
            }
            System.out.println();
        } else if(user instanceof Detective) {
            System.out.print("Other detectives are: ");
            for(Player p : players) {
                if(p instanceof Detective && !p.equals(user))
                    System.out.print("["+p+"] ");
            }
            System.out.println();
        }
    }

    private Player searchPlayer(int id) {
        for(Player p : players)
            if(p.getId() == id && p.isAlive())
                return p;

        return null;
    }

    private Player mafiasKill() {
        HashMap<Player, Integer> map = new HashMap<>();
        int noOfMafiaWithNonZeroHP = 0;
        Player playerToBeKilled = null;

        while(playerToBeKilled == null) {
            noOfMafiaWithNonZeroHP = 0;
            for (Player player : players) {
                if(!(player instanceof Mafia))
                    continue;

                Mafia mafia = (Mafia) player;

                if(mafia.getHP() > 0)
                    noOfMafiaWithNonZeroHP++;
                Player pl = null;
                if(player.equals(user)) {
                    while(pl == null) {
                        try {
                            System.out.print("Choose a player to kill: ");
                            int n = sc.nextInt();
                            pl = searchPlayer(n);
                            if(pl == null)
                                throw new NullPointerException("No such player found!");
                            if(pl instanceof Mafia)
                                throw new SamePlayerTypeException("You cannot kill a mafia!");
                        } catch (InputMismatchException e) {
                            System.out.println("Input not an integer.");
                            sc.next();
                        } catch(SamePlayerTypeException e) {
                            System.out.println(e.getMessage());
                            pl = null;
                        }
                        catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else
                    pl = mafia.voteKill(players);
                if (map.containsKey(pl))
                    map.put(pl, map.get(pl) + 1);
                else
                    map.put(pl, 1);

            }
            if(noOfMafiaWithNonZeroHP <= 0)
                return null;
            int maxCount = 0;
            for(Player player : map.keySet()) {
                int count = map.get(player);
                if(count > maxCount) {
                    playerToBeKilled = player;
                }
                else if(count == maxCount) {
                    playerToBeKilled = null;
                }
            }
        }

        for(Player player : players) {
            if(!(player instanceof Mafia))
                continue;
            Mafia mafia = (Mafia)player;
            if(mafia.getHP()>0) {
                mafia.attackPlayer(playerToBeKilled, noOfMafiaWithNonZeroHP--);
            }
        }
        return playerToBeKilled;
    }

    private Player detectivesTest() {
        HashMap<Player, Integer> map = new HashMap<>();
        Player playerToBeTested = null;
        int noOfAliveDetectives;

        while(playerToBeTested == null) {
            noOfAliveDetectives = 0;
            for (Player pl : players) {
                if(!(pl instanceof Detective))
                    continue;
                Detective detective = (Detective)pl;
                noOfAliveDetectives++;
                Player player = null;
                if(pl.equals(user)) {
                    while(player == null) {
                        try {
                            System.out.print("Choose a player to test: ");
                            int n = sc.nextInt();
                            player = searchPlayer(n);
                            if(player == null)
                                throw new NullPointerException("No such player found!");
                            if(player instanceof Detective)
                                throw new SamePlayerTypeException("You cannot test a detective!");
                        } catch (InputMismatchException e) {
                            System.out.println("Input not an integer.");
                            sc.next();
                        } catch (SamePlayerTypeException e) {
                            System.out.println(e.getMessage());
                            player = null;
                        }
                        catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else
                    player = detective.voteCheck(players);

                if (map.containsKey(player))
                    map.put(player, map.get(player) + 1);
                else
                    map.put(player, 1);
            }

            if(noOfAliveDetectives <= 0)
                return null;
            int maxCount = 0;
            for(Player player : map.keySet()) {
                int count = map.get(player);
                if(count > maxCount) {
                    playerToBeTested = player;
                }
                else if(count == maxCount) {
                    playerToBeTested = null;
                }
            }
        }

        if(playerToBeTested instanceof Mafia) {
            if(user instanceof Detective && user.isAlive())
                System.out.println(playerToBeTested + " is a Mafia.");
            return playerToBeTested;
        }
        if(user instanceof Detective && user.isAlive())
            System.out.println(playerToBeTested+ " is not a mafia.");
        return null;
    }

    private void healersHeal() {
        HashMap<Player, Integer> map = new HashMap<>();
        int noOfAliveHealers;
        Player playerToBeHealed = null;

        while(playerToBeHealed == null) {
            noOfAliveHealers = 0;
            for (Player pl : players) {
                if(!(pl instanceof Healer))
                    continue;
                Healer healer = (Healer)pl;

                noOfAliveHealers++;
                Player player = null;
                if(pl.equals(user)) {
                    while(player == null) {
                        try {
                            System.out.print("Choose a player to heal: ");
                            int n = sc.nextInt();
                            player = searchPlayer(n);
                            if(player == null)
                                throw new NullPointerException("No such player found!");
                        } catch (InputMismatchException e) {
                            System.out.println("Input not an integer.");
                            sc.next();
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else
                    player = healer.voteHeal(players);
                if (map.containsKey(player))
                    map.put(player, map.get(player) + 1);
                else
                    map.put(player, 1);

            }
            if(noOfAliveHealers <= 0)
                return;
            int maxCount = 0;
            for(Player player : map.keySet()) {
                int count = map.get(player);
                if(count > maxCount) {
                    playerToBeHealed = player;
                }
                else if(count == maxCount) {
                    playerToBeHealed = null;
                }
            }
        }

        playerToBeHealed.heal(500);
    }

    private Player voteKick() {
        ArrayList<Vote> map = new ArrayList<>();
        Player playerToBeKicked = null;

        for(int i=0; i<players.get(players.size()-1).getId(); ++i) {
            map.add(new Vote(i+1, 0));
        }

        while(playerToBeKicked == null) {
            for (Player pl : players) {
                Player player = null;
                if(pl.equals(user)) {
                    while(player == null) {
                        try {
                            System.out.print("Select a person to vote out: ");
                            int n = sc.nextInt();
                            player = searchPlayer(n);
                            if(player == null)
                                throw new NullPointerException("No such player found!");
                        } catch (InputMismatchException e) {
                            System.out.println("Input not an integer.");
                            sc.next();
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else
                    player = pl.vote(players);

                map.get(player.getId()-1).increaseVote();

            }
            Collections.sort(map, Collections.reverseOrder());
            if(map.get(0).getVote() == map.get(1).getVote()) {
                playerToBeKicked = null;
                System.out.println("No highest vote encountered. Re-vote.");
            }
            else {
                playerToBeKicked = searchPlayer(map.get(0).getId());
            }
        }

        return playerToBeKicked;
    }

    /**
     * Return values:
     * 0 - Game is still left
     * 1 - Mafias Win
     * 2 - Mafias Defeated
     * @author Harsh Kumar
     */
    public int winner() {
        int cMafia = 0, cRest = 0;
        for(Player player : players) {
            if(player instanceof Mafia)
                cMafia++;
            else
                cRest++;
        }

        if(cMafia == cRest)
            return 1;
        else if(cMafia == 0)
            return 2;
        return 0;
    }

    public void printResult() {
        if(winner() == 0)
            return;
        if(winner() == 1)
            System.out.println("The Mafias have won");
        else
            System.out.println("The Mafias have lost");

        inactivePlayers.addAll(players);
        for(Player p : inactivePlayers) {
            if(p instanceof Mafia)
                System.out.print(p+", ");
        }
        System.out.println("were mafia");
        for(Player p : inactivePlayers) {
            if(p instanceof Detective)
                System.out.print(p+", ");
        }
        System.out.println("were detectives");
        for(Player p : inactivePlayers) {
            if(p instanceof Healer)
                System.out.print(p+", ");
        }
        System.out.println("were healers");
        for(Player p : inactivePlayers) {
            if(p instanceof Commoner)
                System.out.print(p+", ");
        }
        System.out.println("were commoners");
    }

    public void playRound() {
        System.out.print(players.size() + " players are remaining: ");
        for(int i=0; i<players.size(); ++i) {
            System.out.print(players.get(i));
            if(i != players.size()-1)
                System.out.print(", ");
        }
        System.out.println();

        Player attackedByMafias = mafiasKill();
        System.out.println("Mafias have chosen a target.");
        Player toBeRemoved = detectivesTest();
        healersHeal();
        System.out.println("Healers have chosen someone to heal.");

        System.out.println("--End of actions--");

        if(attackedByMafias != null && attackedByMafias.getHP() <= 0) {
            System.out.println(attackedByMafias + " has died.");
            inactivePlayers.add(attackedByMafias);
            attackedByMafias.kill();
            players.remove(attackedByMafias);
        }

        if(toBeRemoved == null)
            toBeRemoved = voteKick();

        System.out.println(toBeRemoved + " has been kicked out.");
        toBeRemoved.kill();
        players.remove(toBeRemoved);
        inactivePlayers.add(toBeRemoved);
    }
}
