package edu.duke.cgp26.battleship;

/**
 * A representation of the board in our game of Battleship
 * @author Charlie Prior
 */
public class BattleShipBoard implements Board {
    private final int width;
    private final int height;

    /**
     * Constructs a BattleShipBoard with the specified width and height
     * @param width the width of the newly constructed board
     * @param height the height of the newly constructed board
     * @throws IllegalArgumentException if the width or height are less than zero
     */
    public BattleShipBoard(int width, int height) {
        if(width <= 0) {
            throw new IllegalArgumentException("BattleShipBoard width must be positive but got " + width);
        }
        if(height <= 0) {
            throw new IllegalArgumentException("BattleShipBoard height must be positive but got " + height);
        }
        this.width = width;
        this.height = height;
    }

    /**
     * Get the width of the BattleShipBoard
     * @return the width of the BattleShipBoard
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the BattleShipBoard
     * @return the height of the BattleShipBoard
     */
    public int getHeight() {
        return height;
    }
}
