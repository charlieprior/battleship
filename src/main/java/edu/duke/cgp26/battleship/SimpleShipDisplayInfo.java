package edu.duke.cgp26.battleship;

/**
 * An interface for displaying information about a ship.
 *
 * @param <T> the representation type.
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
    /**
     * The representation to display when the ship is not hit.
     */
    private final T myData;
    /**
     * The representation to display when the ship is hit.
     */
    private final T onHit;

    /**
     * Construct a SimpleShipDisplayInfo given the representation to display when the ship is not hit and when it is hit.
     *
     * @param myData the representation to display when the ship is not hit.
     * @param onHit  the representation to display when the ship is hit.
     */
    public SimpleShipDisplayInfo(T myData, T onHit) {
        this.myData = myData;
        this.onHit = onHit;
    }

    /**
     * Get the information to display at the given coordinate.
     *
     * @param where the coordinate to display.
     * @param hit   true if the coordinate has been hit, false otherwise.
     * @return the representation to display at the given coordinate.
     */
    @Override
    public T getInfo(Coordinate where, boolean hit) {
        return hit ? onHit : myData;
    }
}
