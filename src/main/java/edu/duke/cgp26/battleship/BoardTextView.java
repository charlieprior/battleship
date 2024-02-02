package edu.duke.cgp26.battleship;

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
    private final Board toDisplay;

    /**
     * Constructs a BoardView, given the Board it will display.
     *
     * @param toDisplay is the Board to display.
     * @throws IllegalArgumentException if the board is larger than 10x26.
     */
    public BoardTextView(Board toDisplay) {
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
        for (int i = 0; i < toDisplay.getWidth(); i++) {
            ans.append(sep);
            ans.append(i);
            sep = "|";
        }
        ans.append("\n");
        return ans.toString();
    }

    /**
     * Displays the player's own board.
     *
     * @return the String that is the representation of the player's own board.
     */
    public String displayMyOwnBoard() {
        StringBuilder ans = new StringBuilder(makeHeader());
        for (int i = 0; i < toDisplay.getHeight(); i++) {
            // Since we enforce the height is < 27, this should be fine
            String rowLetter = String.valueOf((char) ('A' + i));
            ans.append(rowLetter);
            ans.append("  ");
            ans.append("| ".repeat(toDisplay.getWidth() - 1));
            ans.append(" ");
            ans.append(rowLetter);
            ans.append("\n");
        }
        ans.append(makeHeader());
        return ans.toString();
    }
}