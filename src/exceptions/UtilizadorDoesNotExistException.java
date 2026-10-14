package exceptions;

public class UtilizadorDoesNotExistException extends Exception {
    public UtilizadorDoesNotExistException(String id) {
        super("O utilizador " + id + " não existe.");
    }
}