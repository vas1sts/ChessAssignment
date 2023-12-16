package Chess.pieces;

import Chess.board.Board;
import Chess.enums.Color;
import Chess.exceptions.InvalidMoveException;
import Chess.location.Location;

public class King extends Piece {
    public final String name;

    public King(Color color, Board board, Location location) {
        super(color, board, location);
        if (color.equals(Color.BLACK)) name = "Black King";
        else name = "White King";
    }

    public String getName() {
        return name;
    }

    public void moveTo(Location newLocation) throws InvalidMoveException {
        int or = getLocation().getRow();
        int oc = getLocation().getCol();

        int nr = newLocation.getRow();
        int nc = newLocation.getCol();

        if (Math.abs(nc - oc) < 2 && Math.abs(nr - or) < 2) {
            if (getBoard().freeVerticalPath(getLocation(), newLocation) && getBoard().freeHorizontalPath(getLocation(), newLocation)) {
                if (newLocation.isEmpty()) {
                    getBoard().movePiece(getLocation(), newLocation);
                } else {
                    getBoard().movePieceCapturing(getLocation(), newLocation);
                }
                setLocation(newLocation);
            } else {
                throw new InvalidMoveException("The King moves one square horizontally or vertically and cannot move over other pieces!");
            }
        } else {
            throw new InvalidMoveException("The King moves one square horizontally or vertically only!");
        }
    }

    public String toString() {
        if (getColor().equals(Color.WHITE)) return "K";
        return "k";
    }
}
