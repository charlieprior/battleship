package edu.duke.cgp26.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * A class to represent a player in the game.
 */
public class TextPlayer extends Player {
    /**
     * Where to read input from.
     */
    final BufferedReader inputReader;
    /**
     * Where to print output.
     */
    final PrintStream out;

    /**
     * Create a TextPlayer.
     *
     * @param name        The name of the player.
     * @param b           The game board.
     * @param sf          The factory for creating ships.
     * @param inputSource Where to read input from.
     * @param out         Where to print output.
     */
    public TextPlayer(String name, Board<Character> b, AbstractShipFactory<Character> sf, BufferedReader inputSource, PrintStream out) {
        super(name, sf, b);
        this.inputReader = inputSource;
        this.out = out;
    }

    /**
     * Function to print a message to the player.
     *
     * @param s The message to print.
     */
    @Override
    protected void printForSelf(String s) {
        out.println("-".repeat(75));
        out.print(s);
        out.println("-".repeat(75));
    }

    /**
     * Function to print a message to the other player.
     *
     * @param s The message to print.
     */
    @Override
    protected void printForEnemy(String s) {
    }

    /**
     * Reads a Coordinate String and returns it.
     *
     * @param prompt the prompt to display.
     * @return the Coordinate.
     * @throws IOException We will not handle this exception.
     */
    @Override
    public Coordinate getCoordinate(String prompt) throws IOException {
        printForSelf(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("Unexpected end of file\n");
        }
        return new Coordinate(s);
    }

    /**
     * Reads a move type and returns it.
     *
     * @param prompt the prompt to display.
     * @return the move type.
     * @throws IOException We will not handle this exception.
     */
    @Override
    public char getMoveType(String prompt) throws IOException {
        printForSelf(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("Unexpected end of file\n");
        }
        s = s.toUpperCase();
        if (!s.equals("F") && !s.equals("M") && !s.equals("S")) {
            throw new IllegalArgumentException("The move type must be one of F, M or S\n");
        }
        return s.charAt(0);
    }

    /**
     * Reads a placement String and returns it.
     *
     * @param prompt the prompt to display.
     * @return the Placement
     * @throws IOException We will not handle this exception.
     */
    @Override
    public Placement getPlacement(String prompt) throws IOException {
        printForSelf(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("Unexpected end of file\n");
        }
        return new Placement(s);
    }

    @Override
    public void printWin() {
        out.println("-".repeat(75));
        out.println("Player " + name + " has won!");
        out.println("-".repeat(75));
    }

}
