package edu.duke.cgp26.battleship;

/**
 * This class represents an abstract rule checker.
 *
 * @param <T> the representation of the ship.
 */
public abstract class PlacementRuleChecker<T> {
    /**
     * The next rule in the chain.
     */
    private final PlacementRuleChecker<T> next;

    /**
     * Create a new PlacementRuleChecker.
     *
     * @param next the next rule in the chain.
     */
    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }

    /**
     * Check if the placement of theShip on theBoard is legal according to this rule.
     *
     * @param theShip  the ship to check.
     * @param theBoard the board to check.
     * @return true if the placement is legal, false otherwise.
     */
    protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

    /**
     * Check if the placement of theShip on theBoard is legal according to this rule and any subsequent rules.
     *
     * @param theShip  the ship to check.
     * @param theBoard the board to check.
     * @return true if the placement is legal, false otherwise.
     */
    public boolean checkPlacement(Ship<T> theShip, Board<T> theBoard) {
        // if we fail our own rule: stop the placement is not legal
        if (!checkMyRule(theShip, theBoard)) {
            return false;
        }
        // otherwise, ask the rest of the chain.
        if (next != null) {
            return next.checkPlacement(theShip, theBoard);
        }
        // if there are no more rules, then the placement is legal
        return true;
    }
}
