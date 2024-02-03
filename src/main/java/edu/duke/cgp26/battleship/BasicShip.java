package edu.duke.cgp26.battleship;

import java.util.HashMap;

/**
 * This class represents a basic 1x1 Ship in our Battleship game. It
 * specializes the representation to Character. It is mainly a mock
 * class used for testing.
 */
public abstract class BasicShip<T> implements Ship<T> {
    /**
     * The ShipDisplayInfo of the ship.
     */
    protected ShipDisplayInfo<T> myDisplayInfo;
    /**
     * The {@link Coordinate}s the ship occupies.
     * True if hit, false otherwise.
     */
    protected HashMap<Coordinate, Boolean> myPieces;

    /**
     * Construct a BasicShip given an Iterable of Coordinates.
     *
     * @param where         the Coordinates of the ship.
     * @param myDisplayInfo the ShipDisplayInfo of the ship.
     */
    public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
        this.myPieces = new HashMap<>();
        this.myDisplayInfo = myDisplayInfo;
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
    }

    /**
     * Check if this ship occupies the given coordinate.
     *
     * @param where is the Coordinate to check if this Ship occupies
     * @return true if where is inside this ship, false if not.
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    /**
     * Check if this ship has been hit in all of its locations meaning it has been
     * sunk.
     *
     * @return false always.
     */
    @Override
    public boolean isSunk() {
        return false;
    }

    /**
     * Does nothing.
     *
     * @param where specifies the coordinates that were hit.
     * @throws IllegalArgumentException if where is not part of the Ship.
     */
    @Override
    public void recordHitAt(Coordinate where) {
    }

    /**
     * Check if this ship was hit at the specified coordinates. The coordinates must
     * be part of this Ship.
     *
     * @param where is the coordinates to check.
     * @return false always.
     * @throws IllegalArgumentException if the coordinates are not part of this
     *                                  ship.
     */
    @Override
    public boolean wasHitAt(Coordinate where) {
        return false;
    }

    /**
     * Return the Character at the given coordinate. This coordinate
     * must be part of the ship.
     *
     * @param where is the coordinate to return information for
     * @return The view-specific information at that coordinate.
     * @throws IllegalArgumentException if where is not part of the Ship
     */
    @Override
    public T getDisplayInfoAt(Coordinate where) {
        // TODO this is not right.  We need to look up the hit status of this coordinate
        return myDisplayInfo.getInfo(where, false);
    }
}
