package edu.duke.cgp26.battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

/**
 * A class to represent a generic player in the game.
 */
public abstract class Player {
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
     * The ships to place.
     */
    final ArrayList<String> shipsToPlace;
    /**
     * A map of ship names to functions that create ships.
     */
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    /**
     * The number of sonar scans remaining.
     */
    int scansRemaining;
    /**
     * The number of ship moves remaining.
     */
    int movesRemaining;

    /**
     * Create a player.
     *
     * @param name The name of the player.
     * @param sf   The factory for creating ships.
     * @param b    The game board.
     */
    public Player(String name, AbstractShipFactory<Character> sf, Board<Character> b) {
        this.name = name;
        this.shipFactory = sf;
        this.theBoard = b;
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        this.scansRemaining = 3;
        this.movesRemaining = 3;
        this.view = new BoardTextView(theBoard);

        setupShipCreationMap();
        setupShipCreationList();
    }

    /**
     * Function to print a message to the player.
     *
     * @param s The message to print.
     */
    protected abstract void printForSelf(String s);

    /**
     * Function to print a message to the other player.
     *
     * @param s The message to print.
     */
    protected abstract void printForEnemy(String s);

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
    public abstract Coordinate getCoordinate(String prompt) throws IOException;

    /**
     * Reads a move type and returns it.
     *
     * @param prompt the prompt to display.
     * @return the move type.
     * @throws IOException We will not handle this exception.
     */
    public abstract char getMoveType(String prompt) throws IOException;

    /**
     * Reads a placement String and returns it.
     *
     * @param prompt the prompt to display.
     * @return the Placement
     * @throws IOException We will not handle this exception.
     */
    public abstract Placement getPlacement(String prompt) throws IOException;

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
                Placement p = getPlacement("Player " + name + " where do you want to place a " + shipName + "?\n");
                Ship<Character> s = createFn.apply(p);
                String result = theBoard.tryAddShip(s);
                if (result != null) {
                    printForSelf(result);
                    continue;
                }
                printForSelf(view.displayMyOwnBoard());
                return;
            } catch (IllegalArgumentException e) {
                printForSelf(e.getMessage());
            }
        }
    }

    /**
     * Perform the placement phase of the game.
     *
     * @throws IOException We will not handle this exception.
     */
    public void doPlacementPhase() throws IOException {
        printForSelf(view.displayMyOwnBoard());
        printForSelf("Player " + name + ": you are going to place the following ships.\n" +
                "There are two types of rectangular ships:\n" +
                "- 2 rectangular \"Submarines\" ships that are 1x2 \n" +
                "- 3 rectangular \"Destroyers\" that are 1x3\n" +
                "For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right. \n" +
                "\n" +
                "There are also two types of special ships:\n" +
                "- 3 \"Battleships\" that are 4 squares arranged in a T formation\n" +
                "- 2 \"Carriers\" that are 7 squares arranged in a Z formation\n" +
                "For each ship, type the coordinate of the upper left\n" +
                "side of the ship (which may not be included in the ship itself),\n" +
                "followed by either U (for up), D (for down), L (for left) or R (for right).\n" +
                "For example M4D would place a ship occupying the squares\n" +
                "M4, M5, M6 and N5.\n");
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
        printForSelf(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Player " + enemyName + "'s ocean"));
        while (true) {
            try {
                char moveType = getMoveType("Possible actions for Player " + name + ":\n" +
                        "\n" +
                        " F Fire at a square\n" +
                        " M Move a ship to another square (" + movesRemaining + " remaining)\n" +
                        " S Sonar scan (" + scansRemaining + " remaining)\n" +
                        "\n" +
                        "Player " + name + ", what would you like to do?\n");
                if (moveType == 'F') {
                    fire(enemyBoard);
                    return;
                } else if (moveType == 'S') {
                    if (scansRemaining <= 0) {
                        printForSelf("You have no sonar scans remaining.\n");
                        continue;
                    }
                    sonarScan(enemyBoard);
                    return;
                } else if (moveType == 'M') {
                    if (movesRemaining <= 0) {
                        printForSelf("You have no moves remaining.\n");
                        continue;
                    }
                    moveShip();
                    return;
                }
            } catch (IllegalArgumentException e) {
                printForSelf(e.getMessage());
            }
        }
    }

    /**
     * Perform a sonar scan at a given coordinate.
     *
     * @param enemyBoard the enemy's board.
     * @throws IOException We will not handle this exception.
     */
    public void sonarScan(Board<Character> enemyBoard) throws IOException {
        Coordinate c = getCoordinate("Player " + name + " enter the coordinate for your sonar scan:\n");
        HashMap<String, Integer> scanResults = enemyBoard.sonarScan(c);
        StringBuilder str = new StringBuilder();
        HashSet<String> shipTypes = new HashSet<>(shipsToPlace);
        for (String shipType : shipTypes) {
            Integer n = scanResults.getOrDefault(shipType, 0);
            str.append(shipType).append("s occupy ").append(n).append(" square");
            str.append(n != 1 ? "s\n" : "\n");
        }
        scansRemaining -= 1;
        printForSelf(str.toString());
        printForEnemy("Player " + name + " used a special action!\n");
    }

    /**
     * Fire a torpedo at the enemy's board.
     *
     * @param enemyBoard the enemy's board.
     * @throws IOException We will not handle this exception.
     */
    public void fire(Board<Character> enemyBoard) throws IOException {
        Coordinate c = getCoordinate("Player " + name + " enter the coordinate for your attack:\n");
        Ship<Character> s = enemyBoard.fireAt(c);
        if (s == null) {
            printForSelf("You missed!\n");
            printForEnemy("Player " + name + " missed at " + c + "!\n");
        } else {
            if (s.isSunk()) {
                printForSelf("You sunk a " + s.getName() + "!\n");
                printForEnemy("Player " + name + " sunk your " + s.getName() + " at " + c + "!\n");
            } else {
                printForSelf("You hit a " + s.getName() + "!\n");
                printForEnemy("Player " + name + " hit your " + s.getName() + " at " + c + "!\n");
            }
        }
    }

    /**
     * Move a ship to a new location.
     *
     * @throws IOException we will not handle this exception.
     */
    public void moveShip() throws IOException {
        Coordinate c = getCoordinate("Enter the coordinate of the ship you'd like to move:\n");
        Ship<Character> s = theBoard.findShip(c);
        theBoard.tryRemoveShip(s); // Remove the ship so that we can manipulate it without side effects

        Placement oldPlacement = s.getPlacement(); // Save the old placement in case we need to move back
        Placement p = getPlacement("Where would you like to move your " + s.getName() + "\n");
        s.move(p);
        String result = theBoard.tryAddShip(s);

        if (result != null) {
            // Move the ship back
            s.move(oldPlacement);
            theBoard.tryAddShip(s); // This should always succeed since we're moving the ship back
            throw new IllegalArgumentException(result);
        }
        movesRemaining -= 1;

        printForEnemy("Player " + name + " used a special action!\n");
    }

    /**
     * Print a message to the player that they have won.
     */
    public abstract void printWin();
}
