package edu.duke.cgp26.battleship;

import java.util.HashSet;

/**
 * A class representing a Z-shaped Carrier.
 *
 * @param <T> the representation type.
 */
public class CarrierShip<T> extends BasicShip<T> {
    /**
     * Constructor for making a BattleshipShip from its coordinates and a {@link ShipDisplayInfo}
     * for both the ship and the enemy.
     *
     * @param upperLeft        the upper left coordinate and orientation of the ship.
     * @param displayInfo      the ShipDisplayInfo of the ship.
     * @param enemyDisplayInfo the ShipDisplayInfo of the ship from an enemy perspective.
     */
    public CarrierShip(Placement upperLeft, ShipDisplayInfo<T> displayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(upperLeft, makeCoords(upperLeft), displayInfo, enemyDisplayInfo);
    }

    /**
     * Helper constructor to initialize the {@link ShipDisplayInfo}s.
     *
     * @param upperLeft the upper left coordinate and orientation of the ship.
     * @param data      the representation to display when the ship is not hit.
     * @param onHit     the representation to display when the ship is hit.
     */
    public CarrierShip(Placement upperLeft, T data, T onHit) {
        this(upperLeft, new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(null, data));
    }

    /**
     * Make the coordinates of the ship.
     *
     * @param upperLeft the upper left coordinate and orientation of the ship.
     * @return the coordinates of the ship.
     */
    static HashSet<Coordinate> makeCoords(Placement upperLeft) {
        HashSet<Coordinate> res = new HashSet<>();
        if (upperLeft.getPlacement() == 'U') {
            res.add(upperLeft.getWhere());
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn()));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 2, upperLeft.getWhere().getColumn()));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 3, upperLeft.getWhere().getColumn()));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 2, upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 3, upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 4, upperLeft.getWhere().getColumn() + 1));
        } else if (upperLeft.getPlacement() == 'D') {
            res.add(upperLeft.getWhere());
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn()));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 2, upperLeft.getWhere().getColumn()));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 2, upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 3, upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 4, upperLeft.getWhere().getColumn() + 1));
        } else if (upperLeft.getPlacement() == 'L') {
            res.add(new Coordinate(upperLeft.getWhere().getRow(), upperLeft.getWhere().getColumn() + 2));
            res.add(new Coordinate(upperLeft.getWhere().getRow(), upperLeft.getWhere().getColumn() + 3));
            res.add(new Coordinate(upperLeft.getWhere().getRow(), upperLeft.getWhere().getColumn() + 4));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn()));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn() + 2));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn() + 3));
        } else if (upperLeft.getPlacement() == 'R') {
            res.add(new Coordinate(upperLeft.getWhere().getRow(), upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow(), upperLeft.getWhere().getColumn() + 2));
            res.add(new Coordinate(upperLeft.getWhere().getRow(), upperLeft.getWhere().getColumn() + 3));
            res.add(new Coordinate(upperLeft.getWhere().getRow(), upperLeft.getWhere().getColumn() + 4));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn()));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn() + 1));
            res.add(new Coordinate(upperLeft.getWhere().getRow() + 1, upperLeft.getWhere().getColumn() + 2));
        } else {
            throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
        }
        return res;
    }

    /**
     * Get the name of the ship.
     *
     * @return the name of this ship ("Carrier").
     */
    @Override
    public String getName() {
        return "Carrier";
    }
}
