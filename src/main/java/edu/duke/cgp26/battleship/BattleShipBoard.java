package edu.duke.cgp26.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of the board in our game of Battleship.
 *
 * @author Charlie Prior
 */
public class BattleShipBoard<T> implements Board<T> {
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
     * The rule checker for placing ships.
     */
    private final PlacementRuleChecker<T> placementChecker;

    /**
     * Constructs a BattleShipBoard with the specified width, height and placement checker.
     *
     * @param width            the width of the newly constructed board.
     * @param height           the height of the newly constructed board.
     * @param placementChecker the rule checker for placing ships.
     * @throws IllegalArgumentException if the width or height are less than zero.
     */
    public BattleShipBoard(int width, int height, PlacementRuleChecker<T> placementChecker) {
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
    }

    /**
     * Constructs a BattleShipBoard with the specified width and height.
     *
     * @param width  the width of the newly constructed board.
     * @param height the height of the newly constructed board.
     * @throws IllegalArgumentException if the width or height are less than zero.
     */
    public BattleShipBoard(int width, int height) {
        this(width, height, new InBoundsRuleChecker<>(null));
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
     * Try to add a {@link Ship} to the Board.
     *
     * @param toAdd the Ship to add.
     * @return true if successful, false otherwise.
     */
    public boolean tryAddShip(Ship<T> toAdd) {
        myShips.add(toAdd);
        return true;
    }

    /**
     * Represent what is at a {@link Coordinate} on the Board.
     *
     * @param where the Coordinate to look at.
     * @return the representation of what is at that Coordinate.
     */
    public T whatIsAt(Coordinate where) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s.getDisplayInfoAt(where);
            }
        }
        return null;
    }
}
