package edu.duke.cgp26.battleship;

/**
 * An interface for displaying information about a ship.
 *
 * @param <T> the representation type.
 */
public interface ShipDisplayInfo<T> {
    /**
     * Get the information to display at the given coordinate.
     *
     * @param where the coordinate to display.
     * @param hit   true if the coordinate has been hit, false otherwise.
     * @return the representation to display at the given coordinate.
     */
    T getInfo(Coordinate where, boolean hit); // TODO is where ever used?
}