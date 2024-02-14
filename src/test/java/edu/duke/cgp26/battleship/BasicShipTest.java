package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicShipTest {
    AbstractShipFactory<Character> factory = new V2ShipFactory();

    @Test
    public void test_move() {
        Ship<Character> s1 = factory.makeBattleship(new Placement("A0D"));
        s1.move(new Placement("B7L"));
        HashSet<Coordinate> coords = new HashSet<>();
        coords.add(new Coordinate("C7"));
        coords.add(new Coordinate("B8"));
        coords.add(new Coordinate("C8"));
        coords.add(new Coordinate("D8"));
        assertEquals(coords, s1.getCoordinates());
    }

}