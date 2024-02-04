package edu.duke.cgp26.battleship;

/**
 * This class represents a rule checker that checks if a ship is in bounds.
 *
 * @param <T> the representation of the ship.
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

    /**
     * Create a new InBoundsRuleChecker.
     *
     * @param next the next rule in the chain.
     */
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    /**
     * Check if the placement of theShip on theBoard is in bounds.
     *
     * @param theShip  the ship to check.
     * @param theBoard the board to check.
     * @return true if the placement is in bounds, false otherwise.
     */
    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (!(0 <= c.getRow() && c.getRow() < theBoard.getHeight() &&
                    0 <= c.getColumn() && c.getColumn() < theBoard.getWidth())) {
                return false;
            }
        }
        return true;
    }
}
