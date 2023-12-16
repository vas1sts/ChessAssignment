package Chess.location;

import Chess.exceptions.InvalidLocationException;
import Chess.pieces.Piece;

import java.io.Serializable;

public class Location implements Serializable {
    private final static String columns = "abcdefgh";
    private final String location;
    private final int row, column;

    private Piece piece;

    public Location(int r, int c) {
        row = r;
        column = c;

        location = columns.charAt(c) + String.valueOf(r + 1);
    }

    public Location(String loc) throws InvalidLocationException {
        row = Integer.parseInt(loc.substring(1)) - 1;
        column = columns.indexOf(loc.substring(0, 1));
        this.location = loc;
        if (row < 0 || row > 7)
            if (column < 0) {
                throw new InvalidLocationException("Out of bounds");
            }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return column;
    }


    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece p) {
        piece = p;
    }


    public String toString() {
        return location;
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
