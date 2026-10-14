package exceptions;

public class DivisaoDoesNotExistException extends Exception {
    public DivisaoDoesNotExistException(String id) {
        super("A divisão " + id + " não existe.");
    }
}