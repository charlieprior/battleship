package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleShipBoardTest {
    private void addBasicShipAtCoord(Board<Character> b, Coordinate coord) {
        Ship<Character> s = new RectangleShip<>(coord, 's', '*');
        assertNull(b.tryAddShip(s));
    }

    private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expected, boolean forSelf) {
        for (int row = 0; row < b.getHeight(); row++) {
            for (int col = 0; col < b.getWidth(); col++) {
                assertEquals(expected[row][col], forSelf ? b.whatIsAtForSelf(new Coordinate(row, col)) :
                        b.whatIsAtForEnemy(new Coordinate(row, col)));
            }
        }
    }

    @Test
    void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
    }

    @Test
    public void test_whatIsAtForSelf() {
        Board<Character> b1 = new BattleShipBoard<>(3, 3, 'X');
        Character[][] expected1 = {{null, null, null}, {null, null, null}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected1, true);

        addBasicShipAtCoord(b1, new Coordinate(1, 2));
        Character[][] expected2 = {{null, null, null}, {null, null, 's'}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected2, true);

        addBasicShipAtCoord(b1, new Coordinate(0, 1));
        Character[][] expected3 = {{null, 's', null}, {null, null, 's'}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected3, true);

        b1.fireAt(new Coordinate(0, 1));
        Character[][] expected4 = {{null, '*', null}, {null, null, 's'}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected4, true);

        b1.fireAt(new Coordinate(1, 2));
        Character[][] expected5 = {{null, '*', null}, {null, null, '*'}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected5, true);

        b1.fireAt(new Coordinate(2, 2));
        checkWhatIsAtBoard(b1, expected5, true);
    }

    @Test
    public void test_whatIsAtForEnemy() {
        Board<Character> b1 = new BattleShipBoard<>(3, 3, 'X');
        Character[][] expected1 = {{null, null, null}, {null, null, null}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected1, false);

        addBasicShipAtCoord(b1, new Coordinate(1, 2));
        b1.fireAt(new Coordinate(1, 2));
        Character[][] expected2 = {{null, null, null}, {null, null, 's'}, {null, null, null}};
        checkWhatIsAtBoard(b1, expected2, false);

        addBasicShipAtCoord(b1, new Coordinate(0, 1));
        b1.fireAt(new Coordinate(2, 2));
        Character[][] expected3 = {{null, null, null}, {null, null, 's'}, {null, null, 'X'}};
        checkWhatIsAtBoard(b1, expected3, false);
    }

    @Test
    public void test_tryAddShip() {
        V1ShipFactory factory = new V1ShipFactory();
        Board<Character> b1 = new BattleShipBoard<>(6, 6, 'X');
        Ship<Character> s1 = factory.makeSubmarine(new Placement("A0H"));
        assertNull(b1.tryAddShip(s1));

        Ship<Character> s2 = factory.makeBattleship(new Placement("A0V"));
        assertNotNull(b1.tryAddShip(s2));
    }

    @Test
    public void test_fireAt() {
        V1ShipFactory factory = new V1ShipFactory();
        Board<Character> b1 = new BattleShipBoard<>(6, 6, 'X');
        Ship<Character> s1 = factory.makeSubmarine(new Placement("A0H"));
        Ship<Character> s2 = factory.makeCarrier(new Placement("A5V"));
        assertNull(b1.tryAddShip(s1));
        assertNull(b1.tryAddShip(s2));

        assertFalse(s1.isSunk());
        assertSame(s1, b1.fireAt(new Coordinate(0, 0)));
        assertFalse(s1.isSunk());
        assertNull(b1.fireAt(new Coordinate(1, 1)));
        assertSame(s1, b1.fireAt(new Coordinate(0, 1)));
        assertTrue(s1.isSunk());

        assertFalse(s2.isSunk());
        assertSame(s2, b1.fireAt(new Coordinate(0, 5)));
        assertFalse(s2.isSunk());
        assertSame(s2, b1.fireAt(new Coordinate(1, 5)));
        assertFalse(s2.isSunk());
        assertSame(s2, b1.fireAt(new Coordinate(2, 5)));
        assertFalse(s2.isSunk());
        assertSame(s2, b1.fireAt(new Coordinate(3, 5)));
        assertFalse(s2.isSunk());
        assertSame(s2, b1.fireAt(new Coordinate(4, 5)));
        assertFalse(s2.isSunk());
        assertSame(s2, b1.fireAt(new Coordinate(5, 5)));
        assertTrue(s2.isSunk());
    }
}