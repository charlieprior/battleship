package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h);
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

    @Test
    void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

        String prompt = "Please enter a location for a ship:\n";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        for (Placement placement : expected) {
            Placement p = player.readPlacement(prompt);
            assertEquals(p, placement); //did we get the right Placement back
            assertEquals("---------------------------------------------------------------------------\n" +
                            prompt +
                            "---------------------------------------------------------------------------\n",
                    bytes.toString()); //should have printed prompt and newline
            bytes.reset(); //clear out bytes for next time around
        }
    }

    @Test
    void test_do_one_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(3, 5, "B2V", bytes);

        player.doOnePlacement();
        assertEquals("---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2\n" +
                "A  | |  A\n" +
                "B  | |d B\n" +
                "C  | |d C\n" +
                "D  | |d D\n" +
                "E  | |  E\n" +
                "  0|1|2\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
        bytes.reset();
    }

    @Test
    public void test_doPlacementPhase() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(3, 5, "B2V", bytes);
        player.doPlacementPhase();
        assertEquals("---------------------------------------------------------------------------\n" +
                "  0|1|2\n" +
                "A  | |  A\n" +
                "B  | |  B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n" +
                "  0|1|2\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A: you are going to place the following ships (which are all\n" +
                "rectangular). For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right.  You have\n" +
                "\n" +
                "2 \"Submarines\" ships that are 1x2 \n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2\n" +
                "A  | |  A\n" +
                "B  | |d B\n" +
                "C  | |d C\n" +
                "D  | |d D\n" +
                "E  | |  E\n" +
                "  0|1|2\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }
}