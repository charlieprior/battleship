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
     * @return null if the ship can be placed, a description of the error otherwise.
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (!(0 <= c.getRow())) {
                return "That placement is invalid: the ship goes off the top of the board.\n";
            }
            if (!(c.getRow() < theBoard.getHeight())) {
                return "That placement is invalid: the ship goes off the bottom of the board.\n";
            }
            if (!(0 <= c.getColumn())) {
                return "That placement is invalid: the ship goes off the left of the board.\n";
            }
            if (!(c.getColumn() < theBoard.getWidth())) {
                return "That placement is invalid: the ship goes off the right of the board.\n";
            }
        }
        return null;
    }
}
