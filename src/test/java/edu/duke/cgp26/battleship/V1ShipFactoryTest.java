package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V1ShipFactoryTest {
    V1ShipFactory factory = new V1ShipFactory();

    private void checkShip(Ship<Character> testShip, String expectedName,
                           char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate c : expectedLocs) {
            assertTrue(testShip.occupiesCoordinates(c));
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
        }
    }

    @Test
    public void test_makeSubmarine() {
        Ship<Character> s1 = factory.makeSubmarine(new Placement("B2H"));
        checkShip(s1, "Submarine", 's',
                new Coordinate("B2"),
                new Coordinate("B3"));

        Ship<Character> s2 = factory.makeSubmarine(new Placement("C4V"));
        checkShip(s2, "Submarine", 's',
                new Coordinate("C4"),
                new Coordinate("D4"));

        assertThrows(IllegalArgumentException.class, () -> factory.makeSubmarine(new Placement("G5J")));
    }

    @Test
    public void test_makeBattleship() {
        Ship<Character> s1 = factory.makeBattleship(new Placement("M7V"));
        checkShip(s1, "Battleship", 'b',
                new Coordinate("M7"),
                new Coordinate("N7"),
                new Coordinate("O7"),
                new Coordinate("P7"));

        Ship<Character> s2 = factory.makeBattleship(new Placement("I2H"));
        checkShip(s2, "Battleship", 'b',
                new Coordinate("I2"),
                new Coordinate("I3"),
                new Coordinate("I4"),
                new Coordinate("I5"));

        assertThrows(IllegalArgumentException.class, () -> factory.makeBattleship(new Placement("G5J")));
        assertThrows(IllegalArgumentException.class, () -> factory.makeCarrier(new Placement("G5U")));
    }

    @Test
    public void test_makeCarrier() {
        Ship<Character> s1 = factory.makeCarrier(new Placement("A1V"));
        checkShip(s1, "Carrier", 'c',
                new Coordinate("A1"),
                new Coordinate("B1"),
                new Coordinate("C1"),
                new Coordinate("D1"),
                new Coordinate("E1"),
                new Coordinate("F1"));

        Ship<Character> s2 = factory.makeCarrier(new Placement("B3H"));
        checkShip(s2, "Carrier", 'c',
                new Coordinate("B3"),
                new Coordinate("B4"),
                new Coordinate("B5"),
                new Coordinate("B6"),
                new Coordinate("B7"),
                new Coordinate("B8"));

        assertThrows(IllegalArgumentException.class, () -> factory.makeCarrier(new Placement("G5J")));
    }

    @Test
    public void test_makeDestroyer() {
        Ship<Character> s1 = factory.makeDestroyer(new Placement("H8V"));
        checkShip(s1, "Destroyer", 'd',
                new Coordinate("H8"),
                new Coordinate("I8"),
                new Coordinate("J8"));

        Ship<Character> s2 = factory.makeDestroyer(new Placement("Y2H"));
        checkShip(s2, "Destroyer", 'd',
                new Coordinate("Y2"),
                new Coordinate("Y3"),
                new Coordinate("Y4"));

        assertThrows(IllegalArgumentException.class, () -> factory.makeDestroyer(new Placement("G5J")));
    }

}