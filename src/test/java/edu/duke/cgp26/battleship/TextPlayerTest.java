package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h);
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

    @Test
    void test_invalid_orientation() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "A0Q\nA0V", bytes);
        AbstractShipFactory<Character> factory = new V1ShipFactory();

        player.doOnePlacement("Destroyer", factory::makeDestroyer);
        assertEquals("---------------------------------------------------------------------------\n" +
                        "Player A where do you want to place a Destroyer?\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "That placement is invalid: it does not have the correct format.\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "Player A where do you want to place a Destroyer?\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "  0|1|2|3|4|5|6|7|8|9\n" +
                        "A d| | | | | | | | |  A\n" +
                        "B d| | | | | | | | |  B\n" +
                        "C d| | | | | | | | |  C\n" +
                        "D  | | | | | | | | |  D\n" +
                        "E  | | | | | | | | |  E\n" +
                        "F  | | | | | | | | |  F\n" +
                        "G  | | | | | | | | |  G\n" +
                        "H  | | | | | | | | |  H\n" +
                        "I  | | | | | | | | |  I\n" +
                        "J  | | | | | | | | |  J\n" +
                        "K  | | | | | | | | |  K\n" +
                        "L  | | | | | | | | |  L\n" +
                        "M  | | | | | | | | |  M\n" +
                        "N  | | | | | | | | |  N\n" +
                        "O  | | | | | | | | |  O\n" +
                        "P  | | | | | | | | |  P\n" +
                        "Q  | | | | | | | | |  Q\n" +
                        "R  | | | | | | | | |  R\n" +
                        "S  | | | | | | | | |  S\n" +
                        "T  | | | | | | | | |  T\n" +
                        "  0|1|2|3|4|5|6|7|8|9\n" +
                        "---------------------------------------------------------------------------\n",
                bytes.toString());
    }

    @Test
    void test_invalid_placement_string() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "AAV\nA0V", bytes);
        AbstractShipFactory<Character> factory = new V1ShipFactory();

        player.doOnePlacement("Destroyer", factory::makeDestroyer);
        assertEquals("---------------------------------------------------------------------------\n" +
                        "Player A where do you want to place a Destroyer?\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "The second character of the coordinate must be a number\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "Player A where do you want to place a Destroyer?\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "  0|1|2|3|4|5|6|7|8|9\n" +
                        "A d| | | | | | | | |  A\n" +
                        "B d| | | | | | | | |  B\n" +
                        "C d| | | | | | | | |  C\n" +
                        "D  | | | | | | | | |  D\n" +
                        "E  | | | | | | | | |  E\n" +
                        "F  | | | | | | | | |  F\n" +
                        "G  | | | | | | | | |  G\n" +
                        "H  | | | | | | | | |  H\n" +
                        "I  | | | | | | | | |  I\n" +
                        "J  | | | | | | | | |  J\n" +
                        "K  | | | | | | | | |  K\n" +
                        "L  | | | | | | | | |  L\n" +
                        "M  | | | | | | | | |  M\n" +
                        "N  | | | | | | | | |  N\n" +
                        "O  | | | | | | | | |  O\n" +
                        "P  | | | | | | | | |  P\n" +
                        "Q  | | | | | | | | |  Q\n" +
                        "R  | | | | | | | | |  R\n" +
                        "S  | | | | | | | | |  S\n" +
                        "T  | | | | | | | | |  T\n" +
                        "  0|1|2|3|4|5|6|7|8|9\n" +
                        "---------------------------------------------------------------------------\n",
                bytes.toString());
    }

    @Test
    void test_invalid_ship_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "A9H\nA0V", bytes);
        AbstractShipFactory<Character> factory = new V1ShipFactory();

        player.doOnePlacement("Destroyer", factory::makeDestroyer);
        assertEquals("---------------------------------------------------------------------------\n" +
                        "Player A where do you want to place a Destroyer?\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "That placement is invalid: the ship goes off the right of the board.\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "Player A where do you want to place a Destroyer?\n" +
                        "---------------------------------------------------------------------------\n" +
                        "---------------------------------------------------------------------------\n" +
                        "  0|1|2|3|4|5|6|7|8|9\n" +
                        "A d| | | | | | | | |  A\n" +
                        "B d| | | | | | | | |  B\n" +
                        "C d| | | | | | | | |  C\n" +
                        "D  | | | | | | | | |  D\n" +
                        "E  | | | | | | | | |  E\n" +
                        "F  | | | | | | | | |  F\n" +
                        "G  | | | | | | | | |  G\n" +
                        "H  | | | | | | | | |  H\n" +
                        "I  | | | | | | | | |  I\n" +
                        "J  | | | | | | | | |  J\n" +
                        "K  | | | | | | | | |  K\n" +
                        "L  | | | | | | | | |  L\n" +
                        "M  | | | | | | | | |  M\n" +
                        "N  | | | | | | | | |  N\n" +
                        "O  | | | | | | | | |  O\n" +
                        "P  | | | | | | | | |  P\n" +
                        "Q  | | | | | | | | |  Q\n" +
                        "R  | | | | | | | | |  R\n" +
                        "S  | | | | | | | | |  S\n" +
                        "T  | | | | | | | | |  T\n" +
                        "  0|1|2|3|4|5|6|7|8|9\n" +
                        "---------------------------------------------------------------------------\n",
                bytes.toString());
    }

    @Test
    void test_empty_file() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "", bytes);
        assertThrows(EOFException.class, () -> player.readPlacement(""));
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
        AbstractShipFactory<Character> factory = new V1ShipFactory();

        player.doOnePlacement("Destroyer", factory::makeDestroyer);
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
        TextPlayer player = createTextPlayer(10, 20, "A0H\n" +
                "C0V\n" +
                "B3V\n" +
                "O5V\n" +
                "G6H\n" +
                "m0h\n" +
                "j9v\n" +
                "r3h\n" +
                "s0h\n" +
                "f3v", bytes);
        player.doPlacementPhase();
        assertEquals("---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
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
                "Player A where do you want to place a Submarine?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C  | | | | | | | | |  C\n" +
                "D  | | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Submarine?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | | | | | | | |  B\n" +
                "C s| | | | | | | | |  C\n" +
                "D s| | | | | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | |d|d|d|  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Battleship?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | |d|d|d|  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L\n" +
                "M b|b|b|b| | | | | |  M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Battleship?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | |d|d|d|  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |b J\n" +
                "K  | | | | | | | | |b K\n" +
                "L  | | | | | | | | |b L\n" +
                "M b|b|b|b| | | | | |b M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Battleship?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | |d|d|d|  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |b J\n" +
                "K  | | | | | | | | |b K\n" +
                "L  | | | | | | | | |b L\n" +
                "M b|b|b|b| | | | | |b M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | |b|b|b|b| | |  R\n" +
                "S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Carrier?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | | | | | | | |  F\n" +
                "G  | | | | | |d|d|d|  G\n" +
                "H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |b J\n" +
                "K  | | | | | | | | |b K\n" +
                "L  | | | | | | | | |b L\n" +
                "M b|b|b|b| | | | | |b M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | |b|b|b|b| | |  R\n" +
                "S c|c|c|c|c|c| | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Carrier?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "A s|s| | | | | | | |  A\n" +
                "B  | | |d| | | | | |  B\n" +
                "C s| | |d| | | | | |  C\n" +
                "D s| | |d| | | | | |  D\n" +
                "E  | | | | | | | | |  E\n" +
                "F  | | |c| | | | | |  F\n" +
                "G  | | |c| | |d|d|d|  G\n" +
                "H  | | |c| | | | | |  H\n" +
                "I  | | |c| | | | | |  I\n" +
                "J  | | |c| | | | | |b J\n" +
                "K  | | |c| | | | | |b K\n" +
                "L  | | | | | | | | |b L\n" +
                "M b|b|b|b| | | | | |b M\n" +
                "N  | | | | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | |b|b|b|b| | |  R\n" +
                "S c|c|c|c|c|c| | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }
}