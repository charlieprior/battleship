package edu.duke.cgp26.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The main entry point for our Battleship application.
 */
public class App {
    /**
     * The first player.
     */
    final TextPlayer player1;
    /**
     * The second player.
     */
    final TextPlayer player2;

    /**
     * Create an App with two players.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     */
    public App(TextPlayer player1, TextPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * The entry point of our program.
     *
     * @param args Command line arguments.
     * @throws IOException We will not handle this exception.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
        Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
        V1ShipFactory factory = new V1ShipFactory();

        TextPlayer player1 = new TextPlayer("A", b1, input, System.out, factory);
        TextPlayer player2 = new TextPlayer("B", b2, input, System.out, factory);

        App app = new App(player1, player2);
        app.doPlacementPhase();
    }

    /**
     * Perform the placement phase of the game.
     *
     * @throws IOException We will not handle this exception.
     */
    public void doPlacementPhase() throws IOException {
        player1.doPlacementPhase();
        player2.doPlacementPhase();
    }
}
