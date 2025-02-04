package edu.duke.cgp26.battleship;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a basic ship.
 *
 * @param <T> the representation type.
 */
public abstract class BasicShip<T> implements Ship<T> {
    /**
     * The ShipDisplayInfo of the ship.
     */
    protected ShipDisplayInfo<T> myDisplayInfo;
    /**
     * The ShipDisplayInfo of the ship from an enemy perspective.
     */
    protected ShipDisplayInfo<T> enemyDisplayInfo;
    /**
     * The {@link Coordinate}s the ship occupies.
     * True if hit, false otherwise.
     */
    protected HashMap<Coordinate, Boolean> myPieces;
    /**
     * The {@link Placement} of the ship.
     */
    protected Placement myPlacement;

    /**
     * Construct a BasicShip given an Iterable of Coordinates.
     *
     * @param p                the Placement of the ship.
     * @param where            the Coordinates of the ship.
     * @param myDisplayInfo    the ShipDisplayInfo of the ship.
     * @param enemyDisplayInfo the ShipDisplayInfo of the ship from an enemy perspective.
     */
    public BasicShip(Placement p, Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        this.myPlacement = p;
        this.myPieces = new HashMap<>();
        this.myDisplayInfo = myDisplayInfo;
        this.enemyDisplayInfo = enemyDisplayInfo;
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
    }

    /**
     * Check if the given coordinate is part of this ship.
     *
     * @param c is the coordinate to check.
     */
    protected void checkCoordinateInThisShip(Coordinate c) {
        if (!myPieces.containsKey(c)) {
            throw new IllegalArgumentException("Coordinate " + c + " is not part of the ship.\n");
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
        for (Boolean val : myPieces.values()) {
            if (!val) {
                return false;
            }
        }
        return true;
    }

    /**
     * Does nothing.
     *
     * @param where specifies the coordinates that were hit.
     * @throws IllegalArgumentException if where is not part of the Ship.
     */
    @Override
    public void recordHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        myPieces.put(where, true);
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
        checkCoordinateInThisShip(where);
        return myPieces.get(where);
    }

    /**
     * Return the Character at the given coordinate. This coordinate
     * must be part of the ship.
     *
     * @param where  is the coordinate to return information for.
     * @param myShip is true if the view is for the owner of the ship, false otherwise.
     * @return The view-specific information at that coordinate.
     * @throws IllegalArgumentException if where is not part of the Ship.
     */
    @Override
    public T getDisplayInfoAt(Coordinate where, boolean myShip) {
        checkCoordinateInThisShip(where);
        if (myShip) {
            return myDisplayInfo.getInfo(where, myPieces.get(where));
        } else {
            return enemyDisplayInfo.getInfo(where, myPieces.get(where));
        }
    }

    /**
     * Get all the Coordinates that this Ship occupies.
     *
     * @return An Iterable with the coordinates that this Ship occupies
     */
    @Override
    public Iterable<Coordinate> getCoordinates() {
        return myPieces.keySet();
    }

    /**
     * Get the Placement of this Ship.
     *
     * @return the Placement.
     */
    @Override
    public Placement getPlacement() {
        return myPlacement;
    }

    /**
     * Move the ship to a new Placement.
     *
     * @param where the new Placement.
     */
    @Override
    public void move(Placement where) {
        HashMap<Coordinate, Boolean> newPieces = new HashMap<>();

        int rotationTimes = myPlacement.calculateRotationTimes(where);

        // First rotate the pieces around some pivot point (we choose the upper left)
        HashMap<Coordinate, Boolean> rotatedPieces = new HashMap<>();
        for (Map.Entry<Coordinate, Boolean> entry : myPieces.entrySet()) {
            Coordinate c = entry.getKey();
            Boolean b = entry.getValue();

            Coordinate rotated = c;
            for (int i = 0; i < rotationTimes; i++) {
                rotated = rotated.rotate(where.getWhere());
            }

            rotatedPieces.put(rotated, b);
        }

        // Find new upper left of rotated piece, this is what we need to translate from
        Integer upperrow = null;
        Integer uppercol = null;
        for (Coordinate c : rotatedPieces.keySet()) {
            if (upperrow == null || c.getRow() < upperrow) {
                upperrow = c.getRow();
            }
            if (uppercol == null || c.getColumn() < uppercol) {
                uppercol = c.getColumn();
            }
        }

        // Now translate
        for (Map.Entry<Coordinate, Boolean> entry : rotatedPieces.entrySet()) {
            Coordinate c = entry.getKey();
            Boolean b = entry.getValue();

            Coordinate translated = c.translate(where.getWhere().getRow() - upperrow,
                    where.getWhere().getColumn() - uppercol);

            newPieces.put(translated, b);
        }

        myPieces = newPieces;
        myPlacement = where;
    }
}
