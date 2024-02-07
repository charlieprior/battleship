package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CarrierShipTest {
    private void test_makeCoords_helper(Placement p1, HashSet<Coordinate> expected) {
        HashSet<Coordinate> coords = CarrierShip.makeCoords(p1);
        assertIterableEquals(expected, coords);
    }

    private void test_constructor_helper(Placement p1, HashSet<Coordinate> expected) {
        CarrierShip<Character> bs = new CarrierShip<>(p1, 'b', '*');
        for (Coordinate c : expected) {
            assertTrue(bs.occupiesCoordinates(c));
        }
        assertEquals("Carrier", bs.getName());
    }

    @Test
    void makeCoords() {
        HashSet<Coordinate> coords1 = new HashSet<>();
        coords1.add(new Coordinate(0, 0));
        coords1.add(new Coordinate(1, 0));
        coords1.add(new Coordinate(2, 0));
        coords1.add(new Coordinate(3, 0));
        coords1.add(new Coordinate(2, 1));
        coords1.add(new Coordinate(3, 1));
        coords1.add(new Coordinate(4, 1));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'U'), coords1);

        HashSet<Coordinate> coords2 = new HashSet<>();
        coords2.add(new Coordinate(0, 0));
        coords2.add(new Coordinate(1, 0));
        coords2.add(new Coordinate(2, 0));
        coords2.add(new Coordinate(1, 1));
        coords2.add(new Coordinate(2, 1));
        coords2.add(new Coordinate(3, 1));
        coords2.add(new Coordinate(4, 1));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'D'), coords2);

        HashSet<Coordinate> coords3 = new HashSet<>();
        coords3.add(new Coordinate(0, 1));
        coords3.add(new Coordinate(0, 2));
        coords3.add(new Coordinate(0, 3));
        coords3.add(new Coordinate(0, 4));
        coords3.add(new Coordinate(1, 0));
        coords3.add(new Coordinate(1, 1));
        coords3.add(new Coordinate(1, 2));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'R'), coords3);

        HashSet<Coordinate> coords4 = new HashSet<>();
        coords4.add(new Coordinate(0, 2));
        coords4.add(new Coordinate(0, 3));
        coords4.add(new Coordinate(0, 4));
        coords4.add(new Coordinate(1, 0));
        coords4.add(new Coordinate(1, 1));
        coords4.add(new Coordinate(1, 2));
        coords4.add(new Coordinate(1, 3));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'L'), coords4);

        assertThrows(IllegalArgumentException.class, () -> CarrierShip.makeCoords(
                new Placement(new Coordinate(0, 0), 'H')));
    }

    @Test
    public void test_constructor() {
        HashSet<Coordinate> coords1 = new HashSet<>();
        coords1.add(new Coordinate(0, 0));
        coords1.add(new Coordinate(1, 0));
        coords1.add(new Coordinate(2, 0));
        coords1.add(new Coordinate(3, 0));
        coords1.add(new Coordinate(2, 1));
        coords1.add(new Coordinate(3, 1));
        coords1.add(new Coordinate(4, 1));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'U'), coords1);

        HashSet<Coordinate> coords2 = new HashSet<>();
        coords2.add(new Coordinate(0, 0));
        coords2.add(new Coordinate(1, 0));
        coords2.add(new Coordinate(2, 0));
        coords2.add(new Coordinate(1, 1));
        coords2.add(new Coordinate(2, 1));
        coords2.add(new Coordinate(3, 1));
        coords2.add(new Coordinate(4, 1));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'D'), coords2);

        HashSet<Coordinate> coords3 = new HashSet<>();
        coords3.add(new Coordinate(0, 1));
        coords3.add(new Coordinate(0, 2));
        coords3.add(new Coordinate(0, 3));
        coords3.add(new Coordinate(0, 4));
        coords3.add(new Coordinate(1, 0));
        coords3.add(new Coordinate(1, 1));
        coords3.add(new Coordinate(1, 2));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'R'), coords3);

        HashSet<Coordinate> coords4 = new HashSet<>();
        coords4.add(new Coordinate(0, 2));
        coords4.add(new Coordinate(0, 3));
        coords4.add(new Coordinate(0, 4));
        coords4.add(new Coordinate(1, 0));
        coords4.add(new Coordinate(1, 1));
        coords4.add(new Coordinate(1, 2));
        coords4.add(new Coordinate(1, 3));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'L'), coords4);
    }

}