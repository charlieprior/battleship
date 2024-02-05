package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTextViewTest {
    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
        Board<Character> b1 = new BattleShipBoard<>(w, h, 'X');
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
        assertEquals(expected, view.displayEnemyBoard());
    }

    @Test
    public void test_display_empty_2by2() {
        String expectedHeader = "  0|1\n";
        String expectedBody = "A  |  A\n" +
                "B  |  B\n";
        emptyBoardHelper(2, 2, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_empty_3by2() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody = "A  | |  A\n" +
                "B  | |  B\n";
        emptyBoardHelper(3, 2, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_empty_3by5() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody = "A  | |  A\n" +
                "B  | |  B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n";
        emptyBoardHelper(3, 5, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_filled_3by5() {
        Board<Character> b1 = new BattleShipBoard<>(3, 5, 'X');
        BoardTextView view = new BoardTextView(b1);
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(1, 2), 's', '*'));
        String expected = "  0|1|2\n" +
                "A  | |  A\n" +
                "B  | |s B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n" +
                "  0|1|2\n";
        assertEquals(expected, view.displayMyOwnBoard());
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*'));
        expected = "  0|1|2\n" +
                "A s| |  A\n" +
                "B  | |s B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n" +
                "  0|1|2\n";
        assertEquals(expected, view.displayMyOwnBoard());
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(4, 1), 's', '*'));
        expected = "  0|1|2\n" +
                "A s| |  A\n" +
                "B  | |s B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  |s|  E\n" +
                "  0|1|2\n";
        assertEquals(expected, view.displayMyOwnBoard());

        b1.fireAt(new Coordinate(1, 2));
        expected = "  0|1|2\n" +
                "A s| |  A\n" +
                "B  | |* B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  |s|  E\n" +
                "  0|1|2\n";
        assertEquals(expected, view.displayMyOwnBoard());
        expected = "  0|1|2\n" +
                "A  | |  A\n" +
                "B  | |s B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n" +
                "  0|1|2\n";
        assertEquals(expected, view.displayEnemyBoard());

        b1.fireAt(new Coordinate(0, 1));
        expected = "  0|1|2\n" +
                "A  |X|  A\n" +
                "B  | |s B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n" +
                "  0|1|2\n";
        assertEquals(expected, view.displayEnemyBoard());
    }

    @Test
    public void display_both_boards() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        BoardTextView view1 = new BoardTextView(b1);
        Board<Character> b2 = new BattleShipBoard<>(10, 20, 'X');
        BoardTextView view2 = new BoardTextView(b2);
        AbstractShipFactory<Character> factory = new V1ShipFactory();

        b1.tryAddShip(factory.makeBattleship(new Placement(new Coordinate(0, 0), 'H')));
        b2.tryAddShip(factory.makeCarrier(new Placement(new Coordinate(3, 4), 'V')));
        b1.fireAt(new Coordinate(0, 1));
        b2.fireAt(new Coordinate(3, 4));
        b2.fireAt(new Coordinate(3, 5));

        assertEquals("     Your ocean                             Player B's ocean\n" +
                        "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n" +
                        "A b|*|b|b| | | | | |  A                A  | | | | | | | | |  A\n" +
                        "B  | | | | | | | | |  B                B  | | | | | | | | |  B\n" +
                        "C  | | | | | | | | |  C                C  | | | | | | | | |  C\n" +
                        "D  | | | | | | | | |  D                D  | | | |c|X| | | |  D\n" +
                        "E  | | | | | | | | |  E                E  | | | | | | | | |  E\n" +
                        "F  | | | | | | | | |  F                F  | | | | | | | | |  F\n" +
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
                        "  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n",
                view1.displayMyBoardWithEnemyNextToIt(view2, "Your ocean", "Player B's ocean"));

        Board<Character> b3 = new BattleShipBoard<>(5, 5, 'X');
        BoardTextView view3 = new BoardTextView(b3);
        Board<Character> b4 = new BattleShipBoard<>(5, 5, 'X');
        BoardTextView view4 = new BoardTextView(b4);
        assertEquals("     Your ocean                   Player B's ocean\n" +
                        "  0|1|2|3|4                    0|1|2|3|4\n" +
                        "A  | | | |  A                A  | | | |  A\n" +
                        "B  | | | |  B                B  | | | |  B\n" +
                        "C  | | | |  C                C  | | | |  C\n" +
                        "D  | | | |  D                D  | | | |  D\n" +
                        "E  | | | |  E                E  | | | |  E\n" +
                        "  0|1|2|3|4                    0|1|2|3|4\n",
                view3.displayMyBoardWithEnemyNextToIt(view4, "Your ocean", "Player B's ocean"));
    }

    @Test
    public void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
        Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }
}