package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Disabled
    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    void test_main() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
        assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
        assertNotNull(expectedStream);

        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        try {
            System.setIn(input);
            System.setOut(out);
            App.main(new String[0]);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }

        String expected = new String(expectedStream.readAllBytes());
        String actual = bytes.toString();
        assertEquals(expected, actual);
    }

    private App createGame(String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board1 = new BattleShipBoard<>(6, 6, 'X');
        Board<Character> board2 = new BattleShipBoard<>(6, 6, 'X');

        AbstractShipFactory<Character> factory = new V1ShipFactory();
        TextPlayer player1 = new TextPlayer("A", board1, input, output, factory);
        TextPlayer player2 = new TextPlayer("B", board2, input, output, factory);

        App app = new App(player1, player2);

        board1.tryAddShip(factory.makeSubmarine(new Placement(new Coordinate(0, 0), 'H')));
        board2.tryAddShip(factory.makeSubmarine(new Placement(new Coordinate(0, 0), 'H')));

        return app;
    }

    @Test
    public void test_doAttackingPhase1() throws IOException {
        String inputData = "F\nA0\nF\nA0\nF\nA1\nF\nA1\n";
        OutputStream bytes = new ByteArrayOutputStream();
        App app = createGame(inputData, bytes);
        app.doAttackingPhase();
        assertTrue(app.player2.theBoard.checkIfLost());
    }

    @Test
    public void test_doAttackingPhase2() throws IOException {
        String inputData = "F\nA0\nF\nA0\nF\nA2\nF\nA1\n";
        OutputStream bytes = new ByteArrayOutputStream();
        App app = createGame(inputData, bytes);
        app.doAttackingPhase();
        assertTrue(app.player1.theBoard.checkIfLost());
    }
}