package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleShipBoardTest {
    private void addBasicShipAtCoord(Board<Character> b, Coordinate coord) {
        Ship<Character> s = new RectangleShip<>(coord, 's', '*');
        assertTrue(b.tryAddShip(s));
    }

    private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expected) {
        for (int row = 0; row < b.getHeight(); row++) {
            for (int col = 0; col < b.getWidth(); col++) {
                assertEquals(expected[row][col], b.whatIsAt(new Coordinate(row, col)));
            }
        }
    }

    @Test
    void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20);
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
    }

    @Test
    public void test_whatIsAt() {
        Board<Character> b1 = new BattleShipBoard<>(3, 3);
        Character[][] expected1 = {{null, null, null}, {null, null, null}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected1);

        addBasicShipAtCoord(b1, new Coordinate(1, 2));
        Character[][] expected2 = {{null, null, null}, {null, null, 's'}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected2);

        addBasicShipAtCoord(b1, new Coordinate(0, 1));
        Character[][] expected3 = {{null, 's', null}, {null, null, 's'}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected3);
    }
}