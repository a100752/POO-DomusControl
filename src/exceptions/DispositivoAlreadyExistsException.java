package exceptions;

public class DispositivoAlreadyExistsException extends Exception {
    public DispositivoAlreadyExistsException(String id) {
        super("O dispositivo " + id + " já existe.");
    }
}