package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipShipTest {
    private void test_makeCoords_helper(Placement p1, HashSet<Coordinate> expected) {
        HashSet<Coordinate> coords = BattleshipShip.makeCoords(p1);
        assertIterableEquals(expected, coords);
    }

    private void test_constructor_helper(Placement p1, HashSet<Coordinate> expected) {
        BattleshipShip<Character> bs = new BattleshipShip<>(p1, 'b', '*');
        for (Coordinate c : expected) {
            assertTrue(bs.occupiesCoordinates(c));
        }
        assertEquals("Battleship", bs.getName());
    }

    @Test
    public void test_makeCoords() {
        HashSet<Coordinate> coords1 = new HashSet<>();
        coords1.add(new Coordinate(0, 1));
        coords1.add(new Coordinate(1, 0));
        coords1.add(new Coordinate(1, 1));
        coords1.add(new Coordinate(1, 2));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'U'), coords1);

        HashSet<Coordinate> coords2 = new HashSet<>();
        coords2.add(new Coordinate(0, 0));
        coords2.add(new Coordinate(0, 1));
        coords2.add(new Coordinate(0, 2));
        coords2.add(new Coordinate(1, 1));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'D'), coords2);

        HashSet<Coordinate> coords3 = new HashSet<>();
        coords3.add(new Coordinate(1, 0));
        coords3.add(new Coordinate(0, 1));
        coords3.add(new Coordinate(1, 1));
        coords3.add(new Coordinate(2, 1));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'L'), coords3);

        HashSet<Coordinate> coords4 = new HashSet<>();
        coords4.add(new Coordinate(0, 0));
        coords4.add(new Coordinate(1, 0));
        coords4.add(new Coordinate(2, 0));
        coords4.add(new Coordinate(1, 1));
        test_makeCoords_helper(new Placement(new Coordinate(0, 0), 'R'), coords4);

        assertThrows(IllegalArgumentException.class, () -> BattleshipShip.makeCoords(
                new Placement(new Coordinate(0, 0), 'H')));
    }

    @Test
    public void test_constructor() {
        HashSet<Coordinate> coords1 = new HashSet<>();
        coords1.add(new Coordinate(0, 1));
        coords1.add(new Coordinate(1, 0));
        coords1.add(new Coordinate(1, 1));
        coords1.add(new Coordinate(1, 2));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'U'), coords1);

        HashSet<Coordinate> coords2 = new HashSet<>();
        coords2.add(new Coordinate(0, 0));
        coords2.add(new Coordinate(0, 1));
        coords2.add(new Coordinate(0, 2));
        coords2.add(new Coordinate(1, 1));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'D'), coords2);

        HashSet<Coordinate> coords3 = new HashSet<>();
        coords3.add(new Coordinate(1, 0));
        coords3.add(new Coordinate(0, 1));
        coords3.add(new Coordinate(1, 1));
        coords3.add(new Coordinate(2, 1));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'L'), coords3);

        HashSet<Coordinate> coords4 = new HashSet<>();
        coords4.add(new Coordinate(0, 0));
        coords4.add(new Coordinate(1, 0));
        coords4.add(new Coordinate(2, 0));
        coords4.add(new Coordinate(1, 1));
        test_constructor_helper(new Placement(new Coordinate(0, 0), 'R'), coords4);
    }

}