package edu.duke.cgp26.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a {@link Board} (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 *
 * @author Charlie Prior
 */
public class BoardTextView {
    /**
     * The {@link Board} to display.
     */
    private final Board<Character> toDisplay;

    /**
     * Constructs a BoardView, given the Board it will display.
     *
     * @param toDisplay is the Board to display.
     * @throws IllegalArgumentException if the board is larger than 10x26.
     */
    public BoardTextView(Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
            throw new IllegalArgumentException(
                    "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
        }
    }

    /**
     * This makes the header line, e.g. 0|1|2|3|4\n.
     *
     * @return the String that is the header line for the given board.
     */
    String makeHeader() {
        StringBuilder ans = new StringBuilder("  ");
        String sep = "";
        for (int col = 0; col < toDisplay.getWidth(); col++) {
            ans.append(sep);
            ans.append(col);
            sep = "|";
        }
        ans.append("\n");
        return ans.toString();
    }

    /**
     * Displays a board given a function that can get the character at a given coordinate.
     *
     * @param getSquareFn a function that takes a Coordinate and returns the character at that coordinate.
     * @return the String that is the representation of the player's own board.
     */
    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder ans = new StringBuilder(makeHeader());
        for (int row = 0; row < toDisplay.getHeight(); row++) {
            // Since we enforce the height is < 27, this should be fine
            String rowLetter = String.valueOf((char) ('A' + row));
            ans.append(rowLetter);
            String sep = " ";
            for (int col = 0; col < toDisplay.getWidth(); col++) {
                ans.append(sep);
                Character disp = getSquareFn.apply(new Coordinate(row, col));
                if (disp == null) {
                    disp = ' ';
                }
                ans.append(disp);
                sep = "|";
            }
            ans.append(" ");
            ans.append(rowLetter);
            ans.append("\n");
        }
        ans.append(makeHeader());
        return ans.toString();
    }

    /**
     * Displays our own board.
     *
     * @return the String that is the representation of the player's own board.
     */
    public String displayMyOwnBoard() {
        return displayAnyBoard(toDisplay::whatIsAtForSelf);
    }

    /**
     * Displays the enemy's board.
     *
     * @return the String that is the representation of the enemy's board.
     */
    public String displayEnemyBoard() {
        return displayAnyBoard(toDisplay::whatIsAtForEnemy);
    }

    /**
     * Displays the player's board and the enemy's board side by side.
     *
     * @param enemyView   is the BoardTextView for the enemy's board.
     * @param myHeader    is the header for the player's board.
     * @param enemyHeader is the header for the enemy's board.
     * @return the String that is the representation of the player's own board and the enemy's board side by side.
     */
    public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
        StringBuilder ans = new StringBuilder();
        ans.append(" ".repeat(5));
        ans.append(myHeader);
        ans.append(" ".repeat(2 * toDisplay.getWidth() + 19 - myHeader.length()));
        ans.append(enemyHeader);
        ans.append("\n");

        String myBoard = displayMyOwnBoard();
        String enemyBoard = enemyView.displayEnemyBoard();

        String[] myBoardLines = myBoard.split("\n");
        String[] enemyBoardLines = enemyBoard.split("\n");

        assert (myBoardLines.length == enemyBoardLines.length);
        for (int i = 0; i < myBoardLines.length; i++) {
            ans.append(myBoardLines[i]);
            ans.append(" ".repeat(2 * toDisplay.getWidth() + 19 - myBoardLines[i].length()));
            ans.append(enemyBoardLines[i]);
            ans.append("\n");
        }

        return ans.toString();
    }
}