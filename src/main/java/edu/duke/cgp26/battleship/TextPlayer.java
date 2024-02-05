package edu.duke.cgp26.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

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
     * The ships to place.
     */
    final ArrayList<String> shipsToPlace;
    /**
     * A map of ship names to functions that create ships.
     */
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

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
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();

        setupShipCreationMap();
        setupShipCreationList();
    }

    /**
     * Helper function to print message with a line of dashes above and below.
     *
     * @param s The message to print.
     */
    private void print(String s) {
        out.println("-".repeat(75));
        out.print(s);
        out.println("-".repeat(75));
    }

    /**
     * Set up the ship creation map.
     */
    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", shipFactory::makeSubmarine);
        shipCreationFns.put("Destroyer", shipFactory::makeDestroyer);
        shipCreationFns.put("Battleship", shipFactory::makeBattleship);
        shipCreationFns.put("Carrier", shipFactory::makeCarrier);
    }

    /**
     * Set up the ship creation list.
     */
    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    /**
     * Reads a Coordinate String and returns it.
     *
     * @param prompt the prompt to display.
     * @return the Coordinate.
     * @throws IOException We will not handle this exception.
     */
    public Coordinate readCoordinate(String prompt) throws IOException {
        print(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("Unexpected end of file\n");
        }
        return new Coordinate(s);
    }

    /**
     * Reads a placement String and returns it.
     *
     * @param prompt the prompt to display.
     * @return the Placement
     * @throws IOException We will not handle this exception.
     */
    public Placement readPlacement(String prompt) throws IOException {
        print(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("Unexpected end of file\n");
        }
        return new Placement(s);
    }

    /**
     * Reads a placement String from the input reader, places it, and displays the Board.
     *
     * @param shipName the name of the ship to place.
     * @param createFn the function to create the ship.
     * @throws IOException We will not handle this exception.
     */
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        while (true) {
            try {
                Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?\n");
                Ship<Character> s = createFn.apply(p);
                String result = theBoard.tryAddShip(s);
                if (result != null) {
                    print(result);
                    continue;
                }
                print(view.displayMyOwnBoard());
                return;
            } catch (IllegalArgumentException e) {
                print(e.getMessage());
            }
        }
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
        for (String s : shipsToPlace) {
            doOnePlacement(s, shipCreationFns.get(s));
        }
    }

    /**
     * Play one turn of the attacking phase of the game.
     *
     * @param enemyBoard the enemy's board.
     * @param enemyView  the view of the enemy's board.
     * @param enemyName  the name of the enemy.
     * @throws IOException We will not handle this exception.
     */
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
        print(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean"));
        while (true) {
            try {
                Coordinate c = readCoordinate("Player " + name + " enter the coordinate for your attack:\n");
                if (enemyBoard.whatIsAtForEnemy(c) != null) {
                    // We've already looked at this coordinate
                    print("That coordinate has already been selected, please choose another.\n");
                    continue;
                }
                Ship<Character> s = enemyBoard.fireAt(c);
                if (s == null) {
                    print("You missed!\n");
                } else {
                    if (s.isSunk()) {
                        print("You sunk a " + s.getName() + "!\n");
                    } else {
                        print("You hit a " + s.getName() + "!\n");
                    }
                }
                return;
            } catch (IllegalArgumentException e) {
                print(e.getMessage());
            }
        }
    }

    /**
     * Print a message to the player that they have won.
     */
    public void printWin() {
        print("Player " + name + " has won!\n");
    }
}
