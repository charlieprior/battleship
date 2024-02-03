package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RectangleShipTest {

    private void test_makeCoords_helper(Coordinate c1, int width, int height, HashSet<Coordinate> expected) {
        HashSet<Coordinate> coords = RectangleShip.makeCoords(c1, width, height);
        assertIterableEquals(expected, coords);
    }

    private void test_constructor_helper(Coordinate c1, int width, int height, HashSet<Coordinate> expected) {
        RectangleShip<Character> rs = new RectangleShip<>(c1, width, height, 's', '*');
        for (Coordinate c : expected) {
            assertTrue(rs.occupiesCoordinates(c));
        }
    }

    @Test
    public void test_makeCoords() {
        HashSet<Coordinate> coords1 = new HashSet<>();
        coords1.add(new Coordinate(1, 2));
        coords1.add(new Coordinate(2, 2));
        coords1.add(new Coordinate(3, 2));
        test_makeCoords_helper(new Coordinate(1, 2), 1, 3, coords1);

        HashSet<Coordinate> coords2 = new HashSet<>();
        coords2.add(new Coordinate(4, 5));
        coords2.add(new Coordinate(5, 5));
        coords2.add(new Coordinate(4, 6));
        coords2.add(new Coordinate(5, 6));
        coords2.add(new Coordinate(4, 7));
        coords2.add(new Coordinate(5, 7));
        test_makeCoords_helper(new Coordinate(4, 5), 3, 2, coords2);
    }

    @Test
    public void test_constructor() {
        HashSet<Coordinate> coords1 = new HashSet<>();
        coords1.add(new Coordinate(1, 2));
        coords1.add(new Coordinate(2, 2));
        coords1.add(new Coordinate(3, 2));
        test_constructor_helper(new Coordinate(1, 2), 1, 3, coords1);

        HashSet<Coordinate> coords2 = new HashSet<>();
        coords2.add(new Coordinate(4, 5));
        coords2.add(new Coordinate(5, 5));
        coords2.add(new Coordinate(4, 6));
        coords2.add(new Coordinate(5, 6));
        coords2.add(new Coordinate(4, 7));
        coords2.add(new Coordinate(5, 7));
        test_constructor_helper(new Coordinate(4, 5), 3, 2, coords2);
    }

    @Test
    public void test_recordWasHit() {
        RectangleShip<Character> rs = new RectangleShip<>(new Coordinate(1, 2), 1, 3, 's', '*');
        rs.recordHitAt(new Coordinate(1, 2));
        assertTrue(rs.wasHitAt(new Coordinate(1, 2)));

        rs.recordHitAt(new Coordinate(2, 2));
        assertTrue(rs.wasHitAt(new Coordinate(2, 2)));

        assertThrows(IllegalArgumentException.class, () -> rs.recordHitAt(new Coordinate(1, 3)));
        assertThrows(IllegalArgumentException.class, () -> rs.wasHitAt(new Coordinate(1, 3)));
    }

    @Test
    public void test_isSunk() {
        RectangleShip<Character> rs = new RectangleShip<>(new Coordinate(1, 2), 1, 3, 's', '*');
        assertFalse(rs.isSunk());
        rs.recordHitAt(new Coordinate(1, 2));
        assertFalse(rs.isSunk());
        rs.recordHitAt(new Coordinate(2, 2));
        assertFalse(rs.isSunk());
        rs.recordHitAt(new Coordinate(3, 2));
        assertTrue(rs.isSunk());
    }

    @Test
    public void test_getDisplayInfo() {
        RectangleShip<Character> rs = new RectangleShip<>(new Coordinate(1, 2), 1, 3, 's', '*');
        assertEquals('s', rs.getDisplayInfoAt(new Coordinate(1, 2)));
        rs.recordHitAt(new Coordinate(1, 2));
        assertEquals('*', rs.getDisplayInfoAt(new Coordinate(1, 2)));
        assertThrows(IllegalArgumentException.class, () -> rs.getDisplayInfoAt(new Coordinate(1, 4)));
    }
}