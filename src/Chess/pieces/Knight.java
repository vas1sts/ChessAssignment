package Chess.pieces;

import Chess.board.Board;
import Chess.enums.Color;
import Chess.exceptions.InvalidMoveException;
import Chess.location.Location;

public class Knight extends Piece {
    public final String name;

    public Knight(Color color, Board board, Location location) {
        super(color, board, location);
        if (color.equals(Color.BLACK)) name = "Black Knight";
        else name = "White Knight";
    }

    public String getName() {
        return name;
    }

    public void moveTo(Location newLocation) throws InvalidMoveException {
        int or = getLocation().getRow();
        int oc = getLocation().getCol();

        int nr = newLocation.getRow();
        int nc = newLocation.getCol();

        if ((Math.abs(nc - oc) == 2 && Math.abs(nr - or) == 1) || (Math.abs(nc - oc) == 1 && Math.abs(nr - or) == 2)) {
            if (newLocation.isEmpty())
                getBoard().movePiece(getLocation(), newLocation);
            else
                getBoard().movePieceCapturing(getLocation(), newLocation);
            setLocation(newLocation);
        } else {
            throw new InvalidMoveException("The Knight moves in an L-shape!");
        }
    }

    public String toString() {
        if (getColor().equals(Color.WHITE)) return "N";
        return "n";
    }
}
