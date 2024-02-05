package edu.duke.cgp26.battleship;

/**
 * This class represents a factory for creating ships in Version 1 of the battleship game.
 */
public class V1ShipFactory implements AbstractShipFactory<Character> {
    /**
     * Create a ship of the given type with its upper-left at the indicated position.
     *
     * @param where  is the position and orientation of the ship.
     * @param w      is the width of the ship.
     * @param h      is the height of the ship.
     * @param letter is the letter to use to display the ship.
     * @param name   is the name of the ship.
     * @return the Ship object created.
     */
    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        int width;
        int height;
        if (where.getPlacement() == 'V') {
            width = w;
            height = h;
        } else {
            // Placement is H, rotate the ship
            width = h;
            height = w;
        }
        // TODO: Could add checking here to make sure placement is valid for type of ship (or in V2)
        return new RectangleShip<Character>(name, where.getWhere(), width, height, letter, '*');
    }

    /**
     * Make a submarine.
     *
     * @param where specifies the location and orientation of the ship to make.
     * @return the Ship created for the submarine.
     */
    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createShip(where, 1, 2, 's', "Submarine");
    }

    /**
     * Make a battleship.
     *
     * @param where specifies the location and orientation of the ship to make.
     * @return the Ship created for the battleship.
     */
    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return createShip(where, 1, 4, 'b', "Battleship");
    }

    /**
     * Make a carrier.
     *
     * @param where specifies the location and orientation of the ship to make.
     * @return the Ship created for the carrier.
     */
    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return createShip(where, 1, 6, 'c', "Carrier");
    }

    /**
     * Make a destroyer.
     *
     * @param where specifies the location and orientation of the ship to make.
     * @return the Ship created for the destroyer.
     */
    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }
}
