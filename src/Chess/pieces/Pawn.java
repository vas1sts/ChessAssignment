package Chess.pieces;

import Chess.board.Board;
import Chess.enums.Color;
import Chess.exceptions.InvalidMoveException;
import Chess.location.Location;

public class Pawn extends Piece {
    private final String name;

    public Pawn(Color color, Board board, Location location) {
        super(color, board, location);
        if (color.equals(Color.BLACK)) name = "Black Pawn";
        else name = "White Pawn";
    }

    public String getName() {
        return name;
    }

    public void moveTo(Location newLocation) throws InvalidMoveException {
        int or = getLocation().getRow();
        int oc = getLocation().getCol();

        int nr = newLocation.getRow();
        int nc = newLocation.getCol();

        int forwardDirection = getColor().equals(Color.WHITE) ? 1 : -1;
        int initialRow = getColor().equals(Color.WHITE) ? 1 : 6;

        if (newLocation.isEmpty() && oc == nc && nr == or + forwardDirection) {
            getBoard().movePiece(getLocation(), newLocation);
            setLocation(newLocation);
        } else if (newLocation.isEmpty() && oc == nc && or == initialRow && nr == or + 2 * forwardDirection && getBoard().freeVerticalPath(getLocation(), newLocation)) {
            getBoard().movePiece(getLocation(), newLocation);
            setLocation(newLocation);
        } else if (!newLocation.isEmpty() && nr == or + forwardDirection && (nc - oc == 1 || nc - oc == -1)) {
            getBoard().movePieceCapturing(getLocation(), newLocation);
            setLocation(newLocation);
        } else {
            throw new InvalidMoveException("The Pawn's move is invalid!");
        }
    }

    public String toString() {
        if (getColor().equals(Color.WHITE)) return "P";
        return "p";
    }
}
