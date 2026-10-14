package exceptions;

public class CasaAlreadyExistsException extends Exception {
     public CasaAlreadyExistsException(String id) {
        super("A casa " + id + " já existe."); // chama Exception(String message)
    }

}
