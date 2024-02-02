package edu.duke.cgp26.battleship;

/**
 * A representation of a generic game board.
 *
 * @author Charlie Prior
 */
public interface Board<T> {
    /**
     * Get the width of the Board.
     *
     * @return the width of the Board.
     */

    int getWidth();

    /**
     * Get the height of the Board.
     *
     * @return the height of the Board.
     */
    int getHeight();

    /**
     * Try to add a {@link Ship} to the Board.
     *
     * @param toAdd the Ship to add.
     * @return true if successful, false otherwise.
     */
    boolean tryAddShip(Ship<T> toAdd);

    /**
     * Represent what is at a {@link Coordinate} on the Board.
     *
     * @param where the Coordinate to look at.
     * @return the representation of what is at that Coordinate.
     */
    T whatIsAt(Coordinate where);

}
