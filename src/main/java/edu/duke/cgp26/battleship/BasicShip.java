package edu.duke.cgp26.battleship;

public class BasicShip implements Ship<Character> {
    private final Coordinate myLocation;

    public BasicShip(Coordinate myLocation) {
        this.myLocation = myLocation;
    }

    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return where.equals(myLocation);
    }

    @Override
    public boolean isSunk() {
        return false;
    }

    @Override
    public void recordHitAt(Coordinate where) {
    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        return where.equals(myLocation);
    }

    @Override
    public Character getDisplayInfoAt(Coordinate where) {
        return 's';
    }
}
