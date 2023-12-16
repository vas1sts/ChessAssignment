package Chess.board;

import Chess.enums.Color;
import Chess.location.Location;
import Chess.pieces.*;

import java.io.Serializable;

public class Board implements Serializable {
    private final Location[][] locations;
    private final Piece[][] pieces;


    public Board() {
        locations = new Location[8][8];
        pieces = new Piece[4][8];

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                locations[i][j] = new Location(i, j);
    }

    public void init() {
        int i, j, r = 6;
        Color color = Color.BLACK;

        for (i = 1; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                pieces[i][j] = new Pawn(color, this, locations[r][j]);
                locations[r][j].setPiece(pieces[i][j]);
            }
            r = 1;
            color = color.nextColor();
        }

        pieces[0][0] = new Rook(color, this, locations[7][0]);
        pieces[0][7] = new Rook(color, this, locations[7][7]);

        pieces[0][1] = new Knight(color, this, locations[7][1]);
        pieces[0][6] = new Knight(color, this, locations[7][6]);

        pieces[0][2] = new Bishop(color, this, locations[7][2]);
        pieces[0][5] = new Bishop(color, this, locations[7][5]);

        pieces[0][3] = new Queen(color, this, locations[7][3]);

        pieces[0][4] = new King(color, this, locations[7][4]);

        color = color.nextColor();

        pieces[3][0] = new Rook(color, this, locations[0][0]);
        pieces[3][7] = new Rook(color, this, locations[0][7]);

        pieces[3][1] = new Knight(color, this, locations[0][1]);
        pieces[3][6] = new Knight(color, this, locations[0][6]);

        pieces[3][2] = new Bishop(color, this, locations[0][2]);
        pieces[3][5] = new Bishop(color, this, locations[0][5]);

        pieces[3][3] = new Queen(color, this, locations[0][3]);

        pieces[3][4] = new King(color, this, locations[0][4]);

        Location location;
        for (i = 0; i < 4; i += 3) {
            for (j = 0; j < 8; j++) {
                location = pieces[i][j].getLocation();
                location.setPiece(pieces[i][j]);
            }
        }
    }

    public Location getLocation(int r, int c) {
        return locations[r][c];
    }

    public void movePiece(Location from, Location to) {
        Piece piece = from.getPiece();

        from.setPiece(null);
        to.setPiece(piece);
    }

    public void movePieceCapturing(Location from, Location to) {
        Piece movingPiece = from.getPiece();
        Piece capturedPiece = to.getPiece();

        from.setPiece(null);
        to.setPiece(movingPiece);

        if (capturedPiece != null) {
            System.out.println("Piece " + movingPiece.getName() + " captures piece " + capturedPiece.getName());
        }
    }

    public boolean freeHorizontalPath(Location from, Location to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();

        int toRow = to.getRow();
        int toCol = to.getCol();

        if (fromCol > toCol) {
            int temp = fromCol;
            fromCol = toCol;
            toCol = temp;
        }

        for (int i = fromCol + 1; i < toCol; i++)
            if (!locations[fromRow][i].isEmpty()) return false;

        return true;
    }

    public boolean freeVerticalPath(Location from, Location to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();

        int toRow = to.getRow();
        int toCol = to.getCol();

        if (fromRow > toRow) {
            int temp = fromRow;
            fromRow = toRow;
            toRow = temp;
        }

        for (int i = fromRow + 1; i < toRow; i++)
            if (!locations[i][fromCol].isEmpty()) return false;

        return true;
    }


    public boolean freeDiagonalPath(Location from, Location to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();

        int toRow = to.getRow();
        int toCol = to.getCol();

        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        if (rowDiff != colDiff) return false;

        int rowIncrement = (fromRow < toRow) ? 1 : -1;
        int colIncrement = (fromCol < toCol) ? 1 : -1;

        int currentRow = fromRow + rowIncrement;
        int currentCol = fromCol + colIncrement;

        while (currentRow != toRow) {
            if (!locations[currentRow][currentCol].isEmpty()) return false;
            currentRow += rowIncrement;
            currentCol += colIncrement;
        }

        return true;
    }

    public boolean freeAntidiagonalPath(Location from, Location to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();

        int toRow = to.getRow();
        int toCol = to.getCol();

        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        if (rowDiff != colDiff) return false;

        int rowIncrement = (fromRow < toRow) ? 1 : -1;
        int colIncrement = (fromCol > toCol) ? 1 : -1;

        int currentRow = fromRow + rowIncrement;
        int currentCol = fromCol - colIncrement;

        while (currentRow != toRow) {
            if (!locations[currentRow][currentCol].isEmpty()) return false;
            currentRow += rowIncrement;
            currentCol -= colIncrement;
        }

        return true;
    }

    public String toString() {
        StringBuilder boardString = new StringBuilder("  a b c d e f g h\n");
        for (int i = 7; i >= 0; i--) {
            boardString.append((i + 1)).append(" ");
            for (int j = 0; j < 8; j++) {
                if (locations[i][j].isEmpty()) boardString.append("  ");
                else boardString.append(locations[i][j].getPiece()).append(" ");
            }
            boardString.append((i + 1)).append("\n");
        }
        boardString.append("  a b c d e f g h\n");
        return boardString.toString();
    }
}