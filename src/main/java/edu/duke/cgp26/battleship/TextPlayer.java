package edu.duke.cgp26.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * A class to represent a player in the game.
 */
public class TextPlayer {
    /**
     * The name of the player.
     */
    final String name;
    /**
     * The factory for creating ships.
     */
    final AbstractShipFactory<Character> shipFactory;
    /**
     * The game board.
     */
    final Board<Character> theBoard;
    /**
     * Text view of the game board.
     */
    final BoardTextView view;
    /**
     * Where to read input from.
     */
    final BufferedReader inputReader;
    /**
     * Where to print output.
     */
    final PrintStream out;

    /**
     * Create a TextPlayer.
     *
     * @param name        The name of the player.
     * @param b           The game board.
     * @param inputSource Where to read input from.
     * @param out         Where to print output.
     * @param sf          The factory for creating ships.
     */
    public TextPlayer(String name, Board<Character> b, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> sf) {
        this.name = name;
        this.shipFactory = sf;
        this.theBoard = b;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputSource;
        this.out = out;
    }

    /**
     * Helper function to print message with a line of dashes above and below.
     *
     * @param s The message to print.
     */
    private void print(String s) {
        out.println("---------------------------------------------------------------------------");
        out.print(s);
        out.println("---------------------------------------------------------------------------");
    }

    /**
     * Reads a placement String and places it.
     *
     * @param prompt the placement String.
     * @return the Placement
     * @throws IOException We will not handle this exception.
     */
    public Placement readPlacement(String prompt) throws IOException {
        print(prompt);
        String s = inputReader.readLine();
        return new Placement(s);
    }

    /**
     * Reads a placement String from the input reader, places it, and displays the Board.
     *
     * @throws IOException We will not handle this exception.
     */
    public void doOnePlacement() throws IOException {
        Placement p = readPlacement("Player " + name + " where do you want to place a Destroyer?\n");
        Ship<Character> s = shipFactory.makeDestroyer(p);
        theBoard.tryAddShip(s);
        print(view.displayMyOwnBoard());
    }

    /**
     * Perform the placement phase of the game.
     *
     * @throws IOException We will not handle this exception.
     */
    public void doPlacementPhase() throws IOException {
        print(view.displayMyOwnBoard());
        print("Player " + name + ": you are going to place the following ships (which are all\n" +
                "rectangular). For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right.  You have\n" +
                "\n" +
                "2 \"Submarines\" ships that are 1x2 \n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n");
        doOnePlacement();
    }
}
