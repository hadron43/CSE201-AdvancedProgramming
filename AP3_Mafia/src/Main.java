import Exceptions.NotEnoughPlayersException;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = null;
        int N;
        System.out.println("Welcome to Mafia");
        while(game == null) {
            try {
                System.out.print("Enter number of players: ");
                N = sc.nextInt();
                game = new Game(N);
            }
            catch (InputMismatchException exception) {
                System.out.println("NaN: Invalid Input!");
                sc.next();
            }
            catch (NotEnoughPlayersException exception) {
                System.out.println(exception.getMessage());
                game = null;
            }
        }

        int winner = 0;
        int round = 1;
        while(winner == 0) {
            System.out.println("Round " + round + ": ");
            game.playRound();
            winner = game.winner();
            System.out.println("--End of round "+round+++"--");
        }
        System.out.println("Game Over.");
        game.printResult();
    }
}
