package exceptions;

public class CasaDoesNotExistException extends Exception {
    public CasaDoesNotExistException(String id) {
        super("A casa " + id + " não existe.");
    }
}