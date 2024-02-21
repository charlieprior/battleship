package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AITextPlayerTest {
    private Player createAIPlayer(int w, int h, OutputStream bytes) {
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h, 'X');
        V2ShipFactory shipFactory = new V2ShipFactory();
        return new AITextPlayer("A", board, shipFactory, output);
    }

    @Test
    public void test_getCoordinate() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Player player = createAIPlayer(10, 20, bytes);
        assertTrue(player.getCoordinate("").getColumn() < 10);
        assertTrue(player.getCoordinate("").getRow() < 20);
    }

    @Test
    public void test_getMoveType() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Player player = createAIPlayer(10, 20, bytes);
        assertEquals('F', player.getMoveType(""));
    }

    @Test
    public void test_getPlacement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Player player = createAIPlayer(10, 20, bytes);
        char placement = player.getPlacement("").getPlacement();
        assertTrue(placement == 'V' || placement == 'H' ||
                placement == 'U' || placement == 'D' || placement == 'L' || placement == 'R');
    }

    @Test
    public void test_printForSelf() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Player player = createAIPlayer(10, 20, bytes);
        player.printForSelf("Hello");
        assertEquals("", bytes.toString()); // Prints nothing
    }

    @Test
    public void test_printForEnemy() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Player player = createAIPlayer(10, 20, bytes);
        player.printForEnemy("Hello\n");
        assertEquals("---------------------------------------------------------------------------\n" +
                "Hello\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }

    @Test
    public void test_printWin() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Player player = createAIPlayer(10, 20, bytes);
        player.printWin();
        assertEquals("---------------------------------------------------------------------------\n" +
                "Player A (computer) has won!\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }
}