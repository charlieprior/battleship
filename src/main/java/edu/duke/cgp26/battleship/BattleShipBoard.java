package edu.duke.cgp26.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A representation of the board in our game of Battleship.
 *
 * @author Charlie Prior
 */
public class BattleShipBoard<T> implements Board<T> {
    /**
     * The representation of a miss.
     */
    final T missInfo;
    /**
     * The width.
     */
    private final int width;
    /**
     * The height.
     */
    private final int height;
    /**
     * List of {@link Ship}s this Board contains.
     */
    private final List<Ship<T>> myShips;

    /**
     * The set of enemy misses.
     */
    private final HashSet<Coordinate> enemyMisses;

    /**
     * The rule checker for placing ships.
     */
    private final PlacementRuleChecker<T> placementChecker;

    /**
     * Constructs a BattleShipBoard with the specified width, height and placement checker.
     *
     * @param width            the width of the newly constructed board.
     * @param height           the height of the newly constructed board.
     * @param placementChecker the rule checker for placing ships.
     * @param missInfo         the representation of a miss.
     * @throws IllegalArgumentException if the width or height are less than zero.
     */
    public BattleShipBoard(int width, int height, PlacementRuleChecker<T> placementChecker, T missInfo) {
        this.placementChecker = placementChecker;
        this.myShips = new ArrayList<>();
        if (width <= 0) {
            throw new IllegalArgumentException("BattleShipBoard width must be positive but got " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("BattleShipBoard height must be positive but got " + height);
        }
        this.width = width;
        this.height = height;
        this.enemyMisses = new HashSet<>();
        this.missInfo = missInfo;
    }

    /**
     * Constructs a BattleShipBoard with the specified width and height.
     *
     * @param width    the width of the newly constructed board.
     * @param height   the height of the newly constructed board.
     * @param missInfo the representation of a miss.
     * @throws IllegalArgumentException if the width or height are less than zero.
     */
    public BattleShipBoard(int width, int height, T missInfo) {
        this(width, height, new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null)), missInfo);
    }

    /**
     * Get the width of the BattleShipBoard.
     *
     * @return the width of the BattleShipBoard.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the BattleShipBoard.
     *
     * @return the height of the BattleShipBoard.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Try to add a {@link Ship} to the Board, according to the placement checker.
     *
     * @param toAdd the Ship to add.
     * @return null if the ship can be placed, a description of the error otherwise.
     */
    public String tryAddShip(Ship<T> toAdd) {
        String result = placementChecker.checkPlacement(toAdd, this);
        if (result == null) {
            myShips.add(toAdd);
        }
        return result;
    }

    /**
     * Represent what is at a {@link Coordinate} on the Board from our perspective.
     *
     * @param where the Coordinate to look at.
     * @return the representation of what is at that Coordinate, or null if unoccupied.
     */
    public T whatIsAtForSelf(Coordinate where) {
        return whatIsAt(where, true);
    }

    /**
     * Represent what is at a {@link Coordinate} on the Board from the enemy's perspective.
     *
     * @param where the Coordinate to look at.
     * @return the representation of what is at that Coordinate or null if unoccupied.
     */
    public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }

    /**
     * Represent what is at a {@link Coordinate} on the Board from any perspective.
     *
     * @param where  the Coordinate to look at.
     * @param isSelf true if the perspective is from our side, false if from the enemy side.
     * @return the representation of what is at that Coordinate, or null if unoccupied.
     */
    protected T whatIsAt(Coordinate where, boolean isSelf) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s.getDisplayInfoAt(where, isSelf);
            }
        }
        return (enemyMisses.contains(where) && !isSelf) ? missInfo : null;
    }

    /**
     * Fire at a given coordinate on the board.
     *
     * @param c the coordinate to fire at.
     * @return the ship that was hit, or null if no ship was hit.
     */
    @Override
    public Ship<T> fireAt(Coordinate c) {
        // Check Coordinate is in bounds
        if (!(0 <= c.getRow())) {
            throw new IllegalArgumentException("That coordinate is invalid: it is off the top of the board.\n");
        }
        if (!(c.getRow() < height)) {
            throw new IllegalArgumentException("That coordinate is invalid: it is off the bottom of the board.\n");
        }
        if (!(0 <= c.getColumn())) {
            throw new IllegalArgumentException("That coordinate is invalid: it is off the left of the board.\n");
        }
        if (!(c.getColumn() < width)) {
            throw new IllegalArgumentException("That coordinate is invalid: it is off the right of the board.\n");
        }
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(c)) {
                s.recordHitAt(c);
                return s;
            }
        }
        enemyMisses.add(c);
        return null;
    }

    /**
     * Generate the Coordinates for a sonar scan at Coordinate c.
     *
     * @param c the Coordinate where to sonar scan.
     * @return the Coordinates of the scan.
     */
    protected HashSet<Coordinate> sonarScanCoordinates(Coordinate c) {
        HashSet<Coordinate> res = new HashSet<>();

        for (int i = 0; i <= 3; i++) {
            for (int j = -i; j <= i; j++) {
                res.add(new Coordinate(c.getRow() + 3 - i, c.getColumn() + j));
                res.add(new Coordinate(c.getRow() + 3 - i, c.getColumn() - j));
                res.add(new Coordinate(c.getRow() - 3 + i, c.getColumn() + j));
                res.add(new Coordinate(c.getRow() - 3 + i, c.getColumn() - j));
            }
        }

        return res;
    }

    /**
     * Perform a sonar scan at a given coordinate.
     *
     * @param where the coordinate to check.
     * @return a map from the representation of ships to the number of said ships.
     */
    @Override
    public HashMap<String, Integer> sonarScan(Coordinate where) {
        HashMap<String, Integer> res = new HashMap<>();
        HashSet<Coordinate> coords = sonarScanCoordinates(where);
        for (Ship<T> s : myShips) {
            for (Coordinate c : coords) {
                if (s.occupiesCoordinates(c)) {
                    Integer val = res.getOrDefault(s.getName(), 0);
                    val += 1;
                    res.put(s.getName(), val);
                }
            }
        }
        return res;
    }

    /**
     * Check if the game has been lost.
     *
     * @return true if the game has been lost, false otherwise.
     */
    @Override
    public boolean checkIfLost() {
        for (Ship<T> s : myShips) {
            if (!s.isSunk()) {
                return false;
            }
        }
        return true;
    }
}
