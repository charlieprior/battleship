package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    private void test_rotate_helper(Coordinate point, Coordinate pivot, Coordinate expected) {
        assertEquals(expected, point.rotate(pivot));
    }

    private void test_translate_helper(Coordinate point, int rowOffset, int colOffset, Coordinate expected) {
        assertEquals(expected, point.translate(rowOffset, colOffset));
    }

    @Test
    public void test_translate() {
        test_translate_helper(new Coordinate(1, 1),
                1, 1,
                new Coordinate(2, 2));

        test_translate_helper(new Coordinate(2, 3),
                1, -1,
                new Coordinate(3, 2));

        test_translate_helper(new Coordinate(4, 5),
                -2, 4,
                new Coordinate(2, 9));
    }

    @Test
    public void test_rotate() {
        test_rotate_helper(new Coordinate(1, 1),
                new Coordinate(0, 0),
                new Coordinate(-1, 1));

        test_rotate_helper(new Coordinate(2, 3),
                new Coordinate(0, 0),
                new Coordinate(-3, 2));

        test_rotate_helper(new Coordinate(5, 4),
                new Coordinate(1, 1),
                new Coordinate(-2, 5));
    }

    @Test
    public void test_row_and_column() {
        Coordinate c1 = new Coordinate(2, 5);
        assertEquals(2, c1.getRow());
        assertEquals(5, c1.getColumn());
    }

    @Test
    public void test_equals() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(1, 3);
        Coordinate c4 = new Coordinate(3, 2);
        assertEquals(c1, c1);   //equals should be reflexive
        assertEquals(c1, c2);   //different objects bu same contents
        assertNotEquals(c1, c3);  //different contents
        assertNotEquals(c1, c4);
        assertNotEquals(c3, c4);
        assertNotEquals(c1, "(1, 2)"); //different types
    }

    @Test
    public void test_toString() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 3);
        Coordinate c3 = new Coordinate(3, 2);
        assertEquals("(1, 2)", c1.toString());
        assertEquals("(1, 3)", c2.toString());
        assertEquals("(3, 2)", c3.toString());
    }

    @Test
    public void test_hashCode() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(0, 3);
        Coordinate c4 = new Coordinate(2, 1);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1.hashCode(), c3.hashCode());
        assertNotEquals(c1.hashCode(), c4.hashCode());
    }

    @Test
    void test_string_constructor_valid_cases() {
        Coordinate c1 = new Coordinate("B3");
        assertEquals(1, c1.getRow());
        assertEquals(3, c1.getColumn());
        Coordinate c2 = new Coordinate("D5");
        assertEquals(3, c2.getRow());
        assertEquals(5, c2.getColumn());
        Coordinate c3 = new Coordinate("A9");
        assertEquals(0, c3.getRow());
        assertEquals(9, c3.getColumn());
        Coordinate c4 = new Coordinate("Z0");
        assertEquals(25, c4.getRow());
        assertEquals(0, c4.getColumn());
        Coordinate c5 = new Coordinate("e7");
        assertEquals(4, c5.getRow());
        assertEquals(7, c5.getColumn());
    }

    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("00"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("AA"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("@0"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("[0"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A/"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A:"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A12"));
    }
}