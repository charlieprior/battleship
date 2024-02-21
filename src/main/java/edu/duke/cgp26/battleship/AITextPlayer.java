package edu.duke.cgp26.battleship;

import java.io.PrintStream;
import java.util.Random;

/**
 * A class to represent an AI player in the game.
 */
public class AITextPlayer extends Player {
    /**
     * Where to print output.
     */
    final PrintStream out;

    /**
     * The random number generator.
     */
    final Random random;

    /**
     * Create an AITextPlayer.
     *
     * @param name The name of the player.
     * @param b    The game board.
     * @param sf   The factory for creating ships.
     * @param out  Where to print output.
     */
    public AITextPlayer(String name, Board<Character> b, AbstractShipFactory<Character> sf, PrintStream out) {
        super(name, sf, b);
        this.out = out;
        random = new Random(123456);
    }


    @Override
    protected void printForSelf(String s) { // AI should print nothing
    }

    @Override
    protected void printForEnemy(String s) {
        out.println("-".repeat(75));
        out.print(s);
        out.println("-".repeat(75));
    }

    @Override
    public Coordinate getCoordinate(String prompt) {
        return new Coordinate(random.nextInt(theBoard.getHeight()),
                random.nextInt(theBoard.getWidth()));
    }

    @Override
    public char getMoveType(String prompt) {
        return 'F'; // Always fire
    }

    @Override
    public Placement getPlacement(String prompt) {
        char[] placements = {'V', 'H', 'U', 'D', 'L', 'R'};
        Coordinate c = getCoordinate(prompt);
        int index = random.nextInt(placements.length);
        return new Placement(c, placements[index]);
    }

    @Override
    public void printWin() {
        out.println("-".repeat(75));
        out.println("Player " + name + " (computer) has won!");
        out.println("-".repeat(75));
    }
}
