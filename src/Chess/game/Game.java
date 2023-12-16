package Chess.game;

import Chess.board.Board;
import Chess.enums.Color;
import Chess.exceptions.InvalidLocationException;
import Chess.exceptions.InvalidMoveException;
import Chess.location.Location;
import Chess.pieces.Piece;

import java.io.*;
import java.util.Scanner;

public class Game {
    private final Scanner input = new Scanner(System.in);
    private Board board;
    private Location location;
    private Piece piece;
    private Color color;


    public Game() {
        board = new Board();
    }

    public void play() {
        String moveString;
        board.init();
        color = Color.WHITE;
        boolean haveWinner = false;
        boolean quit = false;

        while (!haveWinner && !quit) {
            System.out.println(board);
            if (color.equals(Color.WHITE)) System.out.println("Enter the move for White piece");
            else System.out.println("Enter the move for Black piece");
            moveString = input.nextLine();
            if (moveString.startsWith(":")) {
                handleCommands(moveString.substring(1));
            } else if (!moveString.contains(":")) {
                handleMove(moveString);
            }
        }
    }

    private void handleCommands(String command) {
        switch (command) {
            case "h":
                printHelp();
                break;
            case "s":
                saveGame();
                break;
            case "o":
                loadGame();
                break;
            default:
                System.out.println("Unknown command. Please use ':h' for help.");
                break;
        }
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println(":h – Display help text");
        System.out.println(":s – Save game");
        System.out.println(":o – Load game");
        System.out.println(":x – Load game");
        System.out.println("\n");

    }

    private void saveGame() {
        System.out.println("Enter the file name to save the game:");
        String fileName = input.nextLine();

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(board);

            System.out.println("Game has been saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the game: " + e.getMessage());
        }
    }

    private void loadGame() {
        System.out.println("Do you want to interrupt the current game? (Type 'yes' to interrupt or 'no' to continue)");
        String interrupt = input.nextLine();

        if (interrupt.equalsIgnoreCase("yes")) {
            System.out.println("Enter the file name to load the game:");
            String fileName = input.nextLine();

            try (FileInputStream fileIn = new FileInputStream(fileName);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

                Board loadedBoard = (Board) objectIn.readObject();
                this.board = loadedBoard;

                System.out.println("Game has been loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("An error occurred while loading the game: " + e.getMessage());
            }
        }
    }

    private void handleMove(String moveString) {
        try {
            check(moveString);
            piece.moveTo(location);
            color = color.nextColor();
        } catch (InvalidMoveException | InvalidLocationException e) {
            System.out.println(e.getMessage());
        }

    }

    private void check(String moveString) throws InvalidMoveException, InvalidLocationException {
        Piece piece;
        Location location;

        if (moveString.length() != 4)
            throw new InvalidLocationException("The move must have 4 positions");

        this.location = new Location(moveString.substring(2, 4));
        location = board.getLocation(Integer.parseInt(moveString.substring(1, 2)) - 1, moveString.charAt(0) - 'a');
        this.location = board.getLocation(Integer.parseInt(moveString.substring(3, 4)) - 1, moveString.charAt(2) - 'a');

        this.piece = location.getPiece();
        piece = this.location.getPiece();

        if (this.piece == null)
            throw new InvalidMoveException("There is no piece in this location");

        if (!this.piece.getColor().equals(color))
            throw new InvalidMoveException("You cannot move the opponent's piece");

        if (piece != null && piece.getColor().equals(color))
            throw new InvalidMoveException("You cannot capture your own piece");
    }
}
