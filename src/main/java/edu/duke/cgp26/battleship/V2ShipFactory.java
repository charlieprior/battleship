package edu.duke.cgp26.battleship;

public class V2ShipFactory extends V1ShipFactory {
    /**
     * Make a battleship.
     *
     * @param where specifies the location and orientation of the ship to make.
     * @return the Ship created for the battleship.
     */
    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return new BattleshipShip<>(where, 'b', '*');
    }

    /**
     * Make a carrier.
     *
     * @param where specifies the location and orientation of the ship to make.
     * @return the Ship created for the carrier.
     */
    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return new CarrierShip<>(where, 'c', '*');
    }
}
