package edu.duke.cgp26.battleship;

import java.util.Objects;

/**
 * This class holds an x and y coordinate.
 *
 * @author Charlie Prior
 */
public class Coordinate {
    /**
     * The Coordinate row.
     */
    private final int row;
    /**
     * The Coordinate column.
     */
    private final int column;

    /**
     * Constructs a Coordinate at the specified row and column.
     *
     * @param row    the row.
     * @param column the column.
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Constructs a Coordinate at the specified row and column.
     *
     * @param text the row and column in the format 'A0' etc.
     */
    public Coordinate(String text) {
        if (text.length() != 2) {
            throw new IllegalArgumentException("The coordinate must consist of one letter and one number\n");
        }
        text = text.toUpperCase();
        char rowLetter = text.charAt(0);
        char colLetter = text.charAt(1);

        if (rowLetter < 'A' || rowLetter > 'Z') {
            throw new IllegalArgumentException("The first character of the coordinate must be a letter\n");
        }
        if (colLetter < '0' || colLetter > '9') {
            throw new IllegalArgumentException("The second character of the coordinate must be a number\n");
        }

        this.row = rowLetter - 'A';
        this.column = colLetter - '0';
    }

    /**
     * Get the Coordinate row.
     *
     * @return the Coordinate row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the Coordinate column.
     *
     * @return the Coordinate column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Rotate a coordinate counter-clockwise around a pivot point.
     *
     * @param pivot The pivot point.
     * @return The rotated coordinate.
     */
    public Coordinate rotate(Coordinate pivot) {
        int deltaX = this.column - pivot.column;
        int deltaY = this.row - pivot.row;

        return new Coordinate(pivot.row - deltaX, pivot.column + deltaY);
    }

    /**
     * Translate a Coordinate by a given offset.
     *
     * @param rowOffset the row offset.
     * @param colOffset the column offest.
     * @return the translated Coordinate.
     */
    public Coordinate translate(int rowOffset, int colOffset) {
        return new Coordinate(this.row + rowOffset, this.column + colOffset);
    }

    /**
     * Compares the equality of two Coordinates.
     *
     * @param o the other Coordinate.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    /**
     * Generates a hashcode of the Coordinate.
     *
     * @return the hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Generates a string of the form (x, y) from a Coordinate.
     *
     * @return the String.
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
