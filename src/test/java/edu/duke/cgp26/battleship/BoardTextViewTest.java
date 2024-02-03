package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTextViewTest {
    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
        Board<Character> b1 = new BattleShipBoard<>(w, h);
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
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
        Board<Character> b1 = new BattleShipBoard<>(3, 5);
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
    }

    @Test
    public void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
        Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }
}