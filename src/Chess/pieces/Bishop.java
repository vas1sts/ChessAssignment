package Chess.pieces;

import Chess.board.Board;
import Chess.enums.Color;
import Chess.exceptions.InvalidMoveException;
import Chess.location.Location;

public class Bishop extends Piece {
    public final String name;

    public Bishop(Color color, Board board, Location location) {
        super(color, board, location);
        if (color.equals(Color.BLACK)) name = "Black Bishop";
        else name = "White Bishop";
    }

    public String getName() {
        return name;
    }

    public void moveTo(Location loc) throws InvalidMoveException {
        int or = getLocation().getRow();
        int oc = getLocation().getCol();

        int nr = loc.getRow();
        int nc = loc.getCol();

        int rowDiff = Math.abs(nr - or);
        int colDiff = Math.abs(nc - oc);

        if (rowDiff == colDiff) {
            if (getBoard().freeDiagonalPath(getLocation(), loc)) {
                if (loc.isEmpty()) {
                    getBoard().movePiece(getLocation(), loc);
                } else {
                    getBoard().movePieceCapturing(getLocation(), loc);
                }
                setLocation(loc);
            } else {
                throw new InvalidMoveException("The Bishop moves diagonally only and cannot move over other pieces!");
            }
        } else {
            throw new InvalidMoveException("The Bishop moves diagonally only!");
        }
    }

    public String toString() {
        if (getColor().equals(Color.WHITE)) return "B";
        return "b";
    }
}
