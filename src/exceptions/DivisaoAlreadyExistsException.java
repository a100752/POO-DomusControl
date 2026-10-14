package exceptions;

public class DivisaoAlreadyExistsException extends Exception {
    public DivisaoAlreadyExistsException(String id) {
        super("A divisão " + id + " já existe.");
    }
}