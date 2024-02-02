package edu.duke.cgp26.battleship;

/**
 * This class represents a basic 1x1 Ship in our Battleship game. It
 * specializes the representation to Character. It is mainly a mock
 * class used for testing.
 */
public class BasicShip implements Ship<Character> {
    /**
     * The location of the Ship.
     */
    private final Coordinate myLocation;

    /**
     * Construct a BasicShip given a Coordinate location.
     *
     * @param myLocation where to construct the Ship.
     */
    public BasicShip(Coordinate myLocation) {
        this.myLocation = myLocation;
    }

    /**
     * Check if this ship occupies the given coordinate.
     *
     * @param where is the Coordinate to check if this Ship occupies
     * @return true if where is inside this ship, false if not.
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return where.equals(myLocation);
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
    public Character getDisplayInfoAt(Coordinate where) {
        return 's';
    }
}
