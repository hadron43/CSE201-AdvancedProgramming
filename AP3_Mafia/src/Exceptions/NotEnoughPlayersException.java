package Exceptions;

public class NotEnoughPlayersException extends Exception {
    public NotEnoughPlayersException() {
        super("Not enough players in the lobby!");
    }
    public NotEnoughPlayersException(String s) { super(s); }
}
