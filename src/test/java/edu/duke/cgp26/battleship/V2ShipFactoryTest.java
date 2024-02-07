package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {
    V2ShipFactory factory = new V2ShipFactory();

    private void checkShip(Ship<Character> testShip, String expectedName,
                           char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate c : expectedLocs) {
            assertTrue(testShip.occupiesCoordinates(c));
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
        }
    }

    @Test
    public void test_makeBattleship() {
        Ship<Character> s1 = factory.makeBattleship(new Placement("M7U"));
        checkShip(s1, "Battleship", 'b',
                new Coordinate("M8"),
                new Coordinate("N7"),
                new Coordinate("N8"),
                new Coordinate("N9"));

        Ship<Character> s2 = factory.makeBattleship(new Placement("I2D"));
        checkShip(s2, "Battleship", 'b',
                new Coordinate("I2"),
                new Coordinate("I3"),
                new Coordinate("I4"),
                new Coordinate("J3"));

        Ship<Character> s3 = factory.makeBattleship(new Placement("K1L"));
        checkShip(s3, "Battleship", 'b',
                new Coordinate("K2"),
                new Coordinate("L2"),
                new Coordinate("M2"),
                new Coordinate("L1"));

        Ship<Character> s4 = factory.makeBattleship(new Placement("S4R"));
        checkShip(s4, "Battleship", 'b',
                new Coordinate("S4"),
                new Coordinate("T4"),
                new Coordinate("U4"),
                new Coordinate("T5"));

        assertThrows(IllegalArgumentException.class, () -> factory.makeBattleship(new Placement("G5H")));
        assertThrows(IllegalArgumentException.class, () -> factory.makeBattleship(new Placement("G5V")));
        assertThrows(IllegalArgumentException.class, () -> factory.makeBattleship(new Placement("G5J")));
        assertThrows(IllegalArgumentException.class, () -> factory.makeBattleship(new Placement("G5/")));
    }

    @Test
    public void test_makeCarrier() {
        Ship<Character> s1 = factory.makeCarrier(new Placement("M7U"));
        checkShip(s1, "Carrier", 'c',
                new Coordinate("M7"),
                new Coordinate("N7"),
                new Coordinate("O7"),
                new Coordinate("P7"),
                new Coordinate("O8"),
                new Coordinate("P8"),
                new Coordinate("Q8"));

        Ship<Character> s2 = factory.makeCarrier(new Placement("I2D"));
        checkShip(s2, "Carrier", 'c',
                new Coordinate("I2"),
                new Coordinate("J2"),
                new Coordinate("K2"),
                new Coordinate("J3"),
                new Coordinate("K3"),
                new Coordinate("L3"),
                new Coordinate("M3"));

        Ship<Character> s3 = factory.makeCarrier(new Placement("K1L"));
        checkShip(s3, "Carrier", 'c',
                new Coordinate("K3"),
                new Coordinate("K4"),
                new Coordinate("K5"),
                new Coordinate("L1"),
                new Coordinate("L2"),
                new Coordinate("L3"),
                new Coordinate("L4"));

        Ship<Character> s4 = factory.makeCarrier(new Placement("S4R"));
        checkShip(s4, "Carrier", 'c',
                new Coordinate("S5"),
                new Coordinate("S6"),
                new Coordinate("S7"),
                new Coordinate("S8"),
                new Coordinate("T4"),
                new Coordinate("T5"),
                new Coordinate("T6"));

        assertThrows(IllegalArgumentException.class, () -> factory.makeCarrier(new Placement("G5H")));
        assertThrows(IllegalArgumentException.class, () -> factory.makeCarrier(new Placement("G5V")));
        assertThrows(IllegalArgumentException.class, () -> factory.makeCarrier(new Placement("G5J")));
        assertThrows(IllegalArgumentException.class, () -> factory.makeCarrier(new Placement("G5/")));
    }
}