package Chess.pieces;

import Chess.board.Board;
import Chess.enums.Color;
import Chess.exceptions.InvalidMoveException;
import Chess.location.Location;

public class Rook extends Piece {
    public final String name;

    public Rook(Color color, Board board, Location location) {
        super(color, board, location);
        if (color.equals(Color.BLACK)) name = "Black Rook";
        else name = "White Rook";
    }

    public String getName() {
        return name;
    }

    public void moveTo(Location loc) throws InvalidMoveException {
        int or = getLocation().getRow();
        int oc = getLocation().getCol();

        int nr = loc.getRow();
        int nc = loc.getCol();

        if ((nr == or && nc != oc) || (nr != or && nc == oc)) {
            if (getBoard().freeHorizontalPath(getLocation(), loc) || getBoard().freeVerticalPath(getLocation(), loc)) {
                if (loc.isEmpty()) {
                    getBoard().movePiece(getLocation(), loc);
                } else {
                    getBoard().movePieceCapturing(getLocation(), loc);
                }
                setLocation(loc);
            } else {
                throw new InvalidMoveException("The Rook moves horizontally or vertically only and cannot move over other pieces!");
            }
        } else {
            throw new InvalidMoveException("The Rook moves horizontally or vertically only!");
        }
    }

    public String toString() {
        if (getColor().equals(Color.WHITE)) return "R";
        return "r";
    }
}
