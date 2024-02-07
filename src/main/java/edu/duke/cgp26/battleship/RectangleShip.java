package edu.duke.cgp26.battleship;

import java.util.HashSet;

/**
 * A class representing a rectangular ship.
 *
 * @param <T> the representation type.
 */
public class RectangleShip<T> extends BasicShip<T> {
    /**
     * The name of the ship.
     */
    final String name;

    /**
     * Constructor for making a RectangleShip from its coordinates and a {@link ShipDisplayInfo}
     * for both the ship and the enemy.
     *
     * @param name             the name of the ship.
     * @param upperLeft        the upper left coordinate of the ship.
     * @param width            the ship's width.
     * @param height           the ship's height.
     * @param displayInfo      the ShipDisplayInfo of the ship.
     * @param enemyDisplayInfo the ShipDisplayInfo of the ship from an enemy perspective.
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> displayInfo,
                         ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height), displayInfo, enemyDisplayInfo);
        this.name = name;
    }

    /**
     * Helper constructor to initialize the {@link ShipDisplayInfo}s.
     *
     * @param name      the name of the ship.
     * @param upperLeft the upper left coordinate of the ship.
     * @param width     the ship's width.
     * @param height    the ship's height.
     * @param data      the representation to display when the ship is not hit.
     * @param onHit     the representation to display when the ship is hit.
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    /**
     * Helper constructor for a 1x1 ship.
     *
     * @param upperLeft the upper left coordinate of the ship.
     * @param data      the representation to display when the ship is not hit.
     * @param onHit     the representation to display when the ship is hit.
     */
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("TestShip", upperLeft, 1, 1, data, onHit);
    }

    /**
     * Make the coordinates of the ship.
     *
     * @param upperLeft the upper left coordinate of the ship.
     * @param width     the ship's width.
     * @param height    the ship's height.
     * @return the set of coordinates of the ship.
     */
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> res = new HashSet<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                res.add(new Coordinate(row + upperLeft.getRow(), col + upperLeft.getColumn()));
            }
        }

        return res;
    }

    @Override
    public String getName() {
        return name;
    }
}
