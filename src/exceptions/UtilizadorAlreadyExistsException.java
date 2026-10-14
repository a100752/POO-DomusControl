package exceptions;

public class UtilizadorAlreadyExistsException extends Exception {
    public UtilizadorAlreadyExistsException(String id) {
        super("O utilizador " + id + " já existe.");
    }
}