package edu.duke.cgp26.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * A class to make a player (human or computer).
 */
public class PlayerFactory {
    /**
     * Make a player.
     *
     * @param name        The name of the player.
     * @param b           The game board.
     * @param sf          The factory for creating ships.
     * @param inputSource Where to read input from.
     * @param out         Where to print output.
     * @return The player.
     * @throws IOException We will not handle this exception.
     */
    protected Player makePlayer(String name, Board<Character> b, AbstractShipFactory<Character> sf, BufferedReader inputSource, PrintStream out) throws IOException {
        while (true) {
            out.println("-".repeat(75));
            out.println("Should player " + name + " be a [c]omputer or a [h]uman? Type C or H.");
            out.println("-".repeat(75));

            String s = inputSource.readLine();
            if (s == null) {
                throw new EOFException("Unexpected end of file\n");
            }
            s = s.toUpperCase();
            if (s.equals("C")) {
                return new AITextPlayer(name, b, sf, out);
            } else if (s.equals("H")) {
                return new TextPlayer(name, b, sf, inputSource, out);
            } else {
                out.println("-".repeat(75));
                out.println("Please enter C or H");
                out.println("-".repeat(75));
            }
        }
    }
}
