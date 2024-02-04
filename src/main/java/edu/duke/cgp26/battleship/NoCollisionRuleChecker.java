package edu.duke.cgp26.battleship;

/**
 * This class represents a rule checker that checks if a ship can be placed without colliding with other ships.
 *
 * @param <T> the representation of the ship.
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
    /**
     * Create a new NoCollisionRuleChecker.
     *
     * @param next the next rule in the chain.
     */
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    /**
     * Check if theShip can be placed on the theBoard without colliding with other ships.
     *
     * @param theShip  the ship to check.
     * @param theBoard the board to check.
     * @return true if the ship can be placed, false otherwise.
     */
    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (theBoard.whatIsAt(c) != null) {
                return false;
            }
        }
        return true;
    }
}
