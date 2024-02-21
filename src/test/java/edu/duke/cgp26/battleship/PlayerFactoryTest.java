package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerFactoryTest {
    private void makePlayer(String inputData, OutputStream bytes) throws IOException {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board1 = new BattleShipBoard<>(6, 6, 'X');
        AbstractShipFactory<Character> shipFactory = new V2ShipFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        playerFactory.makePlayer("A", board1, shipFactory, input, output);
    }

    @Test
    public void test_makePlayerC() throws IOException {
        OutputStream bytes = new ByteArrayOutputStream();
        makePlayer("g\nc", bytes);
        assertEquals("---------------------------------------------------------------------------\n" +
                "Should player A be a [c]omputer or a [h]uman? Type C or H.\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Please enter C or H\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Should player A be a [c]omputer or a [h]uman? Type C or H.\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }

    @Test
    public void test_makePlayerH() throws IOException {
        OutputStream bytes = new ByteArrayOutputStream();
        makePlayer("h\n", bytes);
        assertEquals("---------------------------------------------------------------------------\n" +
                "Should player A be a [c]omputer or a [h]uman? Type C or H.\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }

    @Test
    public void test_makePlayerEOF() throws IOException {
        OutputStream bytes = new ByteArrayOutputStream();
        assertThrows(EOFException.class, () -> makePlayer("", bytes));
    }

}