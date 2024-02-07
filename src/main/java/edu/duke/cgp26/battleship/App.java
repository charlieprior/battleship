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
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        Board<Character> b2 = new BattleShipBoard<>(10, 20, 'X');
        V1ShipFactory factory = new V2ShipFactory();

        TextPlayer player1 = new TextPlayer("A", b1, input, System.out, factory);
        TextPlayer player2 = new TextPlayer("B", b2, input, System.out, factory);

        App app = new App(player1, player2);
        app.doPlacementPhase();
        app.doAttackingPhase();
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

    /**
     * Perform one attack.
     *
     * @param attacker The attacking player.
     * @param defender The defending player.
     * @return true if the game is over, false otherwise.
     * @throws IOException We will not handle this exception.
     */
    public boolean doOneAttack(TextPlayer attacker, TextPlayer defender) throws IOException {
        attacker.playOneTurn(defender.theBoard, defender.view, defender.name);
        if (defender.theBoard.checkIfLost()) {
            attacker.printWin();
            return true;
        }
        return false;
    }

    /**
     * Perform the attacking phase of the game.
     *
     * @throws IOException We will not handle this exception.
     */
    public void doAttackingPhase() throws IOException {
        while (true) {
            if (doOneAttack(player1, player2)) {
                return;
            }
            if (doOneAttack(player2, player1)) {
                return;
            }
        }
    }
}
