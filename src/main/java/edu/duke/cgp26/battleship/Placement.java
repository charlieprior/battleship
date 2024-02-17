package edu.duke.cgp26.battleship;

import java.util.Objects;

/**
 * This class holds a {@link Coordinate} and orientation
 */
public class Placement {
    /**
     * The {@link Coordinate} where the ship is placed.
     */
    private final Coordinate where;
    /**
     * The orientation of the ship.
     */
    private final char placement;

    /**
     * Constructs Placement given a {@link Coordinate} and a char orientation.
     *
     * @param where     the Coordinate location.
     * @param placement the placement orientation.
     */
    public Placement(Coordinate where, char placement) {
        placement = Character.toUpperCase(placement);
        checkValidPlacement(placement);
        this.where = where;
        this.placement = placement;
    }

    /**
     * Constructs Placement given a string of the form "A0V".
     *
     * @param text the placement string of the form "A0V".
     */
    public Placement(String text) {
        if (text.length() != 3) {
            throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
        }
        text = text.toUpperCase();
        Coordinate coordinate = new Coordinate(text.substring(0, 2));
        char placement = text.charAt(2);
        checkValidPlacement(placement);
        this.where = coordinate;
        this.placement = placement;
    }

    /**
     * Check that placement is 'V' or 'H'.
     *
     * @param placement the placement to check.
     */
    private void checkValidPlacement(char placement) {
        if (placement != 'V' && placement != 'H'
                && placement != 'U' && placement != 'D' && placement != 'L' && placement != 'R') {
            throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
        }
    }

    /**
     * Get the {@link Coordinate}.
     *
     * @return the Coordinate.
     */
    public Coordinate getWhere() {
        return where;
    }

    /**
     * Get the placement
     *
     * @return the placement.
     */
    public char getPlacement() {
        return placement;
    }

    /**
     * Calculate how many times this Placement needs to be rotated to match the new Placement.
     *
     * @param where the new Placement.
     * @return the number of rotation times.
     */
    int calculateRotationTimes(Placement where) {
        int rotationTimes = 0;
        if (this.getPlacement() != where.getPlacement()) {
            if (this.getPlacement() == 'H') {
                rotationTimes = 3;
            } else if (this.getPlacement() == 'V') {
                rotationTimes = 1;
            } else if (this.getPlacement() == 'U') {
                if (where.getPlacement() == 'L') {
                    rotationTimes = 1;
                } else if (where.getPlacement() == 'D') {
                    rotationTimes = 2;
                } else if (where.getPlacement() == 'R') {
                    rotationTimes = 3;
                }
            } else if (this.getPlacement() == 'L') {
                if (where.getPlacement() == 'D') {
                    rotationTimes = 1;
                } else if (where.getPlacement() == 'R') {
                    rotationTimes = 2;
                } else if (where.getPlacement() == 'U') {
                    rotationTimes = 3;
                }
            } else if (this.getPlacement() == 'D') {
                if (where.getPlacement() == 'R') {
                    rotationTimes = 1;
                } else if (where.getPlacement() == 'U') {
                    rotationTimes = 2;
                } else if (where.getPlacement() == 'L') {
                    rotationTimes = 3;
                }
            } else if (this.getPlacement() == 'R') {
                if (where.getPlacement() == 'U') {
                    rotationTimes = 1;
                } else if (where.getPlacement() == 'L') {
                    rotationTimes = 2;
                } else if (where.getPlacement() == 'D') {
                    rotationTimes = 3;
                }
            }
        }

        return rotationTimes;
    }

    /**
     * Check the equality of two Placements.
     *
     * @param o the other Placement.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placement placement1 = (Placement) o;
        return placement == placement1.placement && Objects.equals(where, placement1.where);
    }

    /**
     * Generates a hashcode of the Placement.
     *
     * @return the hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(where, placement);
    }

    /**
     * Generates a String representation of the Placement of the form "(1, 2)H"
     *
     * @return the String.
     */
    @Override
    public String toString() {
        return where.toString() + placement;
    }
}
