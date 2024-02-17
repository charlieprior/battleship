package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicShipTest {
    AbstractShipFactory<Character> factory = new V2ShipFactory();

    @Test
    public void test_move() {
        Ship<Character> s1 = factory.makeBattleship(new Placement("A0D"));
        s1.recordHitAt(new Coordinate("A0"));
        Placement p1 = new Placement("B7L");
        s1.move(p1);
        HashSet<Coordinate> coords1 = new HashSet<>();
        coords1.add(new Coordinate("C7"));
        coords1.add(new Coordinate("B8"));
        coords1.add(new Coordinate("C8"));
        coords1.add(new Coordinate("D8"));
        assertEquals(coords1, s1.getCoordinates());
        assertEquals(p1, s1.getPlacement());
        assertEquals('b', s1.getDisplayInfoAt(new Coordinate("C7"), true));
        assertEquals('*', s1.getDisplayInfoAt(new Coordinate("B8"), true));
        assertEquals('b', s1.getDisplayInfoAt(new Coordinate("C8"), true));
        assertEquals('b', s1.getDisplayInfoAt(new Coordinate("D8"), true));

        Ship<Character> s2 = factory.makeCarrier(new Placement("A0R"));
        s2.recordHitAt(new Coordinate("A1"));
        Placement p2 = new Placement("A0D");
        s2.move(p2);
        HashSet<Coordinate> coords2 = new HashSet<>();
        coords2.add(new Coordinate("A0"));
        coords2.add(new Coordinate("B0"));
        coords2.add(new Coordinate("C0"));
        coords2.add(new Coordinate("B1"));
        coords2.add(new Coordinate("C1"));
        coords2.add(new Coordinate("D1"));
        coords2.add(new Coordinate("E1"));
        assertEquals(coords2, s2.getCoordinates());
        assertEquals(p2, s2.getPlacement());
        assertEquals('c', s2.getDisplayInfoAt(new Coordinate("A0"), true));
        assertEquals('c', s2.getDisplayInfoAt(new Coordinate("B0"), true));
        assertEquals('c', s2.getDisplayInfoAt(new Coordinate("C0"), true));
        assertEquals('*', s2.getDisplayInfoAt(new Coordinate("B1"), true));
        assertEquals('c', s2.getDisplayInfoAt(new Coordinate("C1"), true));
        assertEquals('c', s2.getDisplayInfoAt(new Coordinate("D1"), true));
        assertEquals('c', s2.getDisplayInfoAt(new Coordinate("E1"), true));

        Ship<Character> s3 = factory.makeSubmarine(new Placement("C3V"));
        s3.recordHitAt(new Coordinate("C3"));
        Placement p3 = new Placement("A1H");
        s3.move(p3);
        HashSet<Coordinate> coords3 = new HashSet<>();
        coords3.add(new Coordinate("A1"));
        coords3.add(new Coordinate("A2"));
        assertEquals(coords3, s3.getCoordinates());
        assertEquals(p3, s3.getPlacement());
        assertEquals('*', s3.getDisplayInfoAt(new Coordinate("A1"), true));
        assertEquals('s', s3.getDisplayInfoAt(new Coordinate("A2"), true));
    }

}