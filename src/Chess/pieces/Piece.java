package Chess.pieces;

import Chess.board.Board;
import Chess.enums.Color;
import Chess.exceptions.InvalidMoveException;
import Chess.location.Location;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    private final Color color;
    private final Board board;
    private Location location;


    public Piece(Color color, Board board, Location location) {
        this.color = color;
        this.board = board;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location newLocation) {
        location = newLocation;
    }

    public abstract void moveTo(Location newLocation) throws InvalidMoveException;

    public Color getColor() {
        return color;
    }

    public Board getBoard() {
        return board;
    }

    public abstract String getName();
}
