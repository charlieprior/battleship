package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h, 'X');
        V2ShipFactory shipFactory = new V2ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

    @Test
    void test_moveShip() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "D5\nA0R\nA0\nE0D", bytes);
        player.theBoard.tryAddShip(player.shipFactory.makeBattleship(new Placement("D4U")));
        player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("E0V")));
        player.moveShip();
        assertThrows(IllegalArgumentException.class, player::moveShip);
        assertEquals("---------------------------------------------------------------------------\n" +
                "Enter the coordinate of the ship you'd like to move:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Where would you like to move your Battleship\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Enter the coordinate of the ship you'd like to move:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Where would you like to move your Battleship\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }

    @Test
    void test_sonarScan() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "A&\nD5", bytes);
        TextPlayer opponent = createTextPlayer(10, 20, "", bytes);
        opponent.theBoard.tryAddShip(opponent.shipFactory.makeBattleship(new Placement("D4U")));
        opponent.theBoard.tryAddShip(opponent.shipFactory.makeSubmarine(new Placement("A4V")));
        opponent.theBoard.tryAddShip(opponent.shipFactory.makeCarrier(new Placement("B6D")));
        assertThrows(IllegalArgumentException.class, () -> player.sonarScan(opponent.theBoard));
        player.sonarScan(opponent.theBoard);
        assertEquals("---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your sonar scan:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your sonar scan:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Submarines occupy 1 square\n" +
                "Destroyers occupy 0 squares\n" +
                "Carriers occupy 6 squares\n" +
                "Battleships occupy 4 squares\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
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
        assertThrows(EOFException.class, () -> player.readCoordinate(""));
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
    void test_do_one_placement2() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(5, 5, "A1D", bytes);
        AbstractShipFactory<Character> factory = new V2ShipFactory();

        player.doOnePlacement("Battleship", factory::makeBattleship);
        assertEquals("---------------------------------------------------------------------------\n" +
                "Player A where do you want to place a Battleship?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "  0|1|2|3|4\n" +
                "A  |b|b|b|  A\n" +
                "B  | |b| |  B\n" +
                "C  | | | |  C\n" +
                "D  | | | |  D\n" +
                "E  | | | |  E\n" +
                "  0|1|2|3|4\n" +
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
                "m0u\n" +
                "j8r\n" +
                "r3u\n" +
                "h0r\n" +
                "e3l", bytes);
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
                "Player A: you are going to place the following ships.\n" +
                "There are two types of rectangular ships:\n" +
                "- 2 rectangular \"Submarines\" ships that are 1x2 \n" +
                "- 3 rectangular \"Destroyers\" that are 1x3\n" +
                "For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right. \n" +
                "\n" +
                "There are also two types of special ships:\n" +
                "- 3 \"Battleships\" that are 4 squares arranged in a T formation\n" +
                "- 2 \"Carriers\" that are 7 squares arranged in a Z formation\n" +
                "For each ship, type the coordinate of the upper left\n" +
                "side of the ship (which may not be included in the ship itself),\n" +
                "followed by either U (for up), D (for down), L (for left) or R (for right).\n" +
                "For example M4D would place a ship occupying the squares\n" +
                "M4, M5, M6 and N5.\n" +
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
                "M  |b| | | | | | | |  M\n" +
                "N b|b|b| | | | | | |  N\n" +
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
                "J  | | | | | | | |b|  J\n" +
                "K  | | | | | | | |b|b K\n" +
                "L  | | | | | | | |b|  L\n" +
                "M  |b| | | | | | | |  M\n" +
                "N b|b|b| | | | | | |  N\n" +
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
                "J  | | | | | | | |b|  J\n" +
                "K  | | | | | | | |b|b K\n" +
                "L  | | | | | | | |b|  L\n" +
                "M  |b| | | | | | | |  M\n" +
                "N b|b|b| | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | | |b| | | | |  R\n" +
                "S  | | |b|b|b| | | |  S\n" +
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
                "H  |c|c|c|c| | | | |  H\n" +
                "I c|c|c| | | | | | |  I\n" +
                "J  | | | | | | | |b|  J\n" +
                "K  | | | | | | | |b|b K\n" +
                "L  | | | | | | | |b|  L\n" +
                "M  |b| | | | | | | |  M\n" +
                "N b|b|b| | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | | |b| | | | |  R\n" +
                "S  | | |b|b|b| | | |  S\n" +
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
                "E  | | | | |c|c|c| |  E\n" +
                "F  | | |c|c|c|c| | |  F\n" +
                "G  | | | | | |d|d|d|  G\n" +
                "H  |c|c|c|c| | | | |  H\n" +
                "I c|c|c| | | | | | |  I\n" +
                "J  | | | | | | | |b|  J\n" +
                "K  | | | | | | | |b|b K\n" +
                "L  | | | | | | | |b|  L\n" +
                "M  |b| | | | | | | |  M\n" +
                "N b|b|b| | | | | | |  N\n" +
                "O  | | | | |d| | | |  O\n" +
                "P  | | | | |d| | | |  P\n" +
                "Q  | | | | |d| | | |  Q\n" +
                "R  | | | |b| | | | |  R\n" +
                "S  | | |b|b|b| | | |  S\n" +
                "T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }

    @Test
    public void test_fireMove() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player1 = createTextPlayer(10, 20, "", bytes);
        TextPlayer player2 = createTextPlayer(10, 20, "A0\nZ9\nAA\n6]\nA1\nA2\nB1\nD0\nA0\nA4", bytes);
        AbstractShipFactory<Character> factory = new V2ShipFactory();
        Ship<Character> s1 = factory.makeBattleship(new Placement("A0D"));
        player1.theBoard.tryAddShip(s1);
        player2.fire(player1.theBoard);
        assertThrows(IllegalArgumentException.class, () -> player2.fire(player1.theBoard));
        assertThrows(IllegalArgumentException.class, () -> player2.fire(player1.theBoard));
        assertThrows(IllegalArgumentException.class, () -> player2.fire(player1.theBoard));
        player2.fire(player1.theBoard);
        player2.fire(player1.theBoard);
        player2.fire(player1.theBoard);
        player2.fire(player1.theBoard);
        player2.fire(player1.theBoard);
        player2.fire(player1.theBoard);
        assertEquals("---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You hit a Battleship!\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You hit a Battleship!\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You hit a Battleship!\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You sunk a Battleship!\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You missed!\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You sunk a Battleship!\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You missed!\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
        bytes.reset();
    }

    @Test
    void test_printWin() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "", bytes);
        player.printWin();
        assertEquals("---------------------------------------------------------------------------\n" +
                "Player A has won!\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
        bytes.reset();
    }

    @Test
    void test_readMoveType() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player1 = createTextPlayer(10, 20, "F", bytes);
        assertEquals('F', player1.readMoveType(""));
        TextPlayer player2 = createTextPlayer(10, 20, "s", bytes);
        assertEquals('S', player2.readMoveType(""));
        TextPlayer player3 = createTextPlayer(10, 20, "m", bytes);
        assertEquals('M', player3.readMoveType(""));
        TextPlayer player4 = createTextPlayer(10, 20, "g", bytes);
        assertThrows(IllegalArgumentException.class, () -> player4.readMoveType(""));
        TextPlayer player5 = createTextPlayer(10, 20, "", bytes);
        assertThrows(EOFException.class, () -> player5.readMoveType(""));
    }

    @Test
    void test_playOneTurn() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "F\nA4\nS\nD5\nS\nB3\nS\nF4\nS\nM\nD5\nD4R\nM\nA4\nA8H\n" +
                "M\nB6\nB7D\nM\nH\nF\nA0", bytes);
        TextPlayer opponent = createTextPlayer(10, 20, "", bytes);
        opponent.theBoard.tryAddShip(opponent.shipFactory.makeBattleship(new Placement("D4U")));
        player.theBoard.tryAddShip(player.shipFactory.makeBattleship(new Placement("D4U")));
        opponent.theBoard.tryAddShip(opponent.shipFactory.makeSubmarine(new Placement("A4V")));
        player.theBoard.tryAddShip(player.shipFactory.makeSubmarine(new Placement("A4V")));
        opponent.theBoard.tryAddShip(opponent.shipFactory.makeCarrier(new Placement("B6D")));
        player.theBoard.tryAddShip(player.shipFactory.makeCarrier(new Placement("B6D")));
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);
        player.playOneTurn(opponent.theBoard, opponent.view, opponent.name);

        assertEquals("---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | |s| | | | |  A                A  | | | | | | | | |  A\n" +
                "B  | | | |s| |c| | |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | |c|c| |  C                C  | | | | | | | | |  C\n" +
                "D  | | | | |b|c|c| |  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b|b|c| |  E                E  | | | | | | | | |  E\n" +
                "F  | | | | | | |c| |  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (3 remaining)\n" +
                " S Sonar scan (3 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You hit a Submarine!\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | |s| | | | |  A                A  | | | |s| | | | |  A\n" +
                "B  | | | |s| |c| | |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | |c|c| |  C                C  | | | | | | | | |  C\n" +
                "D  | | | | |b|c|c| |  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b|b|c| |  E                E  | | | | | | | | |  E\n" +
                "F  | | | | | | |c| |  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (3 remaining)\n" +
                " S Sonar scan (3 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your sonar scan:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Submarines occupy 1 square\n" +
                "Destroyers occupy 0 squares\n" +
                "Carriers occupy 6 squares\n" +
                "Battleships occupy 4 squares\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | |s| | | | |  A                A  | | | |s| | | | |  A\n" +
                "B  | | | |s| |c| | |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | |c|c| |  C                C  | | | | | | | | |  C\n" +
                "D  | | | | |b|c|c| |  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b|b|c| |  E                E  | | | | | | | | |  E\n" +
                "F  | | | | | | |c| |  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (3 remaining)\n" +
                " S Sonar scan (2 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your sonar scan:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Submarines occupy 2 squares\n" +
                "Destroyers occupy 0 squares\n" +
                "Carriers occupy 1 square\n" +
                "Battleships occupy 0 squares\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | |s| | | | |  A                A  | | | |s| | | | |  A\n" +
                "B  | | | |s| |c| | |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | |c|c| |  C                C  | | | | | | | | |  C\n" +
                "D  | | | | |b|c|c| |  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b|b|c| |  E                E  | | | | | | | | |  E\n" +
                "F  | | | | | | |c| |  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (3 remaining)\n" +
                " S Sonar scan (1 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your sonar scan:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Submarines occupy 0 squares\n" +
                "Destroyers occupy 0 squares\n" +
                "Carriers occupy 1 square\n" +
                "Battleships occupy 4 squares\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | |s| | | | |  A                A  | | | |s| | | | |  A\n" +
                "B  | | | |s| |c| | |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | |c|c| |  C                C  | | | | | | | | |  C\n" +
                "D  | | | | |b|c|c| |  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b|b|c| |  E                E  | | | | | | | | |  E\n" +
                "F  | | | | | | |c| |  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (3 remaining)\n" +
                " S Sonar scan (0 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You have no sonar scans remaining.\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (3 remaining)\n" +
                " S Sonar scan (0 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Enter the coordinate of the ship you'd like to move:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Where would you like to move your Battleship\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | |s| | | | |  A                A  | | | |s| | | | |  A\n" +
                "B  | | | |s| |c| | |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | |c|c| |  C                C  | | | | | | | | |  C\n" +
                "D  | | | |b| |c|c| |  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b| |c| |  E                E  | | | | | | | | |  E\n" +
                "F  | | | |b| | |c| |  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (2 remaining)\n" +
                " S Sonar scan (0 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Enter the coordinate of the ship you'd like to move:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Where would you like to move your Submarine\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | |s|s A                A  | | | |s| | | | |  A\n" +
                "B  | | | | | |c| | |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | |c|c| |  C                C  | | | | | | | | |  C\n" +
                "D  | | | |b| |c|c| |  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b| |c| |  E                E  | | | | | | | | |  E\n" +
                "F  | | | |b| | |c| |  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (1 remaining)\n" +
                " S Sonar scan (0 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Enter the coordinate of the ship you'd like to move:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Where would you like to move your Carrier\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "     Your ocean                             Player A's ocean\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "A  | | | | | | | |s|s A                A  | | | |s| | | | |  A\n" +
                "B  | | | | | | |c| |  B                B  | | | | | | | | |  B\n" +
                "C  | | | | | | |c|c|  C                C  | | | | | | | | |  C\n" +
                "D  | | | |b| | |c|c|  D                D  | | | | | | | | |  D\n" +
                "E  | | | |b|b| | |c|  E                E  | | | | | | | | |  E\n" +
                "F  | | | |b| | | |c|  F                F  | | | | | | | | |  F\n" +
                "G  | | | | | | | | |  G                G  | | | | | | | | |  G\n" +
                "H  | | | | | | | | |  H                H  | | | | | | | | |  H\n" +
                "I  | | | | | | | | |  I                I  | | | | | | | | |  I\n" +
                "J  | | | | | | | | |  J                J  | | | | | | | | |  J\n" +
                "K  | | | | | | | | |  K                K  | | | | | | | | |  K\n" +
                "L  | | | | | | | | |  L                L  | | | | | | | | |  L\n" +
                "M  | | | | | | | | |  M                M  | | | | | | | | |  M\n" +
                "N  | | | | | | | | |  N                N  | | | | | | | | |  N\n" +
                "O  | | | | | | | | |  O                O  | | | | | | | | |  O\n" +
                "P  | | | | | | | | |  P                P  | | | | | | | | |  P\n" +
                "Q  | | | | | | | | |  Q                Q  | | | | | | | | |  Q\n" +
                "R  | | | | | | | | |  R                R  | | | | | | | | |  R\n" +
                "S  | | | | | | | | |  S                S  | | | | | | | | |  S\n" +
                "T  | | | | | | | | |  T                T  | | | | | | | | |  T\n" +
                "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (0 remaining)\n" +
                " S Sonar scan (0 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You have no moves remaining.\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (0 remaining)\n" +
                " S Sonar scan (0 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "The move type must be one of F, M or S\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Possible actions for Player A:\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (0 remaining)\n" +
                " S Sonar scan (0 remaining)\n" +
                "\n" +
                "Player A, what would you like to do?\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "Player A enter the coordinate for your attack:\n" +
                "---------------------------------------------------------------------------\n" +
                "---------------------------------------------------------------------------\n" +
                "You missed!\n" +
                "---------------------------------------------------------------------------\n", bytes.toString());
    }
}