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
     * @return null if the ship can be placed, a description of the error otherwise.
     */
    String tryAddShip(Ship<T> toAdd);

    /**
     * Represent what is at a {@link Coordinate} on the Board from our perspective.
     *
     * @param where the Coordinate to look at.
     * @return the representation of what is at that Coordinate or null if unoccupied.
     */
    T whatIsAtForSelf(Coordinate where);

    /**
     * Represent what is at a {@link Coordinate} on the Board from the enemy's perspective.
     *
     * @param where the Coordinate to look at.
     * @return the representation of what is at that Coordinate or null if unoccupied.
     */
    T whatIsAtForEnemy(Coordinate where);

    /**
     * Fire at a given coordinate on the board.
     *
     * @param c the coordinate to fire at.
     * @return the ship that was hit, or null if no ship was hit.
     */
    Ship<T> fireAt(Coordinate c);
}
