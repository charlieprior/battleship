package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementTest {
    @Test
    public void test_basic_constructor() {
        Coordinate c1 = new Coordinate("B3");
        Placement p1 = new Placement(c1, 'V');
        assertEquals('V', p1.getPlacement());
        assertEquals(c1, p1.getWhere());
    }

    @Test
    public void test_basic_constructor_errors() {
        Coordinate c1 = new Coordinate("B3");
        assertThrows(IllegalArgumentException.class, () -> new Placement(c1, 'g'));
    }

    @Test
    public void test_string_constructor() {
        Placement p1 = new Placement("B3V");
        Coordinate c1 = p1.getWhere();
        assertEquals(1, c1.getRow());
        assertEquals(3, c1.getColumn());
        assertEquals('V', p1.getPlacement());
        Placement p2 = new Placement("D5H");
        Coordinate c2 = p2.getWhere();
        assertEquals(3, c2.getRow());
        assertEquals(5, c2.getColumn());
        assertEquals('H', p2.getPlacement());
        Placement p3 = new Placement("A9V");
        Coordinate c3 = p3.getWhere();
        assertEquals(0, c3.getRow());
        assertEquals(9, c3.getColumn());
        assertEquals('V', p3.getPlacement());
        Placement p4 = new Placement("Z0h");
        Coordinate c4 = p4.getWhere();
        assertEquals(25, c4.getRow());
        assertEquals(0, c4.getColumn());
        assertEquals('H', p4.getPlacement());
        Placement p5 = new Placement("e7v");
        Coordinate c5 = p5.getWhere();
        assertEquals(4, c5.getRow());
        assertEquals(7, c5.getColumn());
        assertEquals('V', p5.getPlacement());
    }

    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("00v"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("AAh"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("@0H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("[0V"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("B1g"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("B20"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("C9&"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("C9hh"));
    }

    @Test
    public void test_equals() {
        Coordinate c1 = new Coordinate("B3");
        Coordinate c2 = new Coordinate("b3");
        Placement p1 = new Placement(c1, 'v');
        Placement p2 = new Placement(c2, 'V');
        Placement p3 = new Placement("D5h");
        Placement p4 = new Placement("D5v");
        Placement p5 = new Placement("C6h");
        Placement p6 = new Placement("c6H");
        assertEquals(p1, p2);
        assertNotEquals(p3, p4);
        assertEquals(p5, p6);
        assertEquals(p1, p1);
        assertNotEquals(p1, null);
        assertNotEquals(p1, c1);
    }

    @Test
    public void test_hashCode() {
        Placement p1 = new Placement("B2v");
        Placement p2 = new Placement("b2V");
        Placement p3 = new Placement("C2H");
        Placement p4 = new Placement("J6V");
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
    }

    @Test
    public void test_toString() {
        Placement p1 = new Placement("B2v");
        Placement p2 = new Placement("C2H");
        Placement p3 = new Placement("J6V");
        assertEquals("(1, 2)V", p1.toString());
        assertEquals("(2, 2)H", p2.toString());
        assertEquals("(9, 6)V", p3.toString());
    }
}