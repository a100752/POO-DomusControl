package exceptions;

public class DispositivoDoesNotExistException extends Exception {
    public DispositivoDoesNotExistException(String id) {
        super("O dispositivo " + id + " não existe.");
    }
}