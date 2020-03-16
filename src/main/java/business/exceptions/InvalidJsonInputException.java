package business.exceptions;

public class InvalidJsonInputException extends Exception {
    public InvalidJsonInputException(String message) {
        super(message);
    }

    public InvalidJsonInputException(String message, Throwable cause) {
        super(message, cause);
    }

}
