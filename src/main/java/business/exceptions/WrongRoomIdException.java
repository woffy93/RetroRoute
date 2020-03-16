package business.exceptions;

public class WrongRoomIdException extends Exception {
    public WrongRoomIdException(String message) {
        super(message);
    }

    public WrongRoomIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
