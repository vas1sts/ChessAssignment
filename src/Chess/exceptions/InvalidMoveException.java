package Chess.exceptions;

public class InvalidMoveException extends Exception {

    public InvalidMoveException() {
    }

    public InvalidMoveException(String msg) {
        super(msg);
    }
}
