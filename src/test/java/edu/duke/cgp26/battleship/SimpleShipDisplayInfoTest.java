package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleShipDisplayInfoTest {

    @Test
    void getInfo() {
        SimpleShipDisplayInfo<Character> charDisplayInfo = new SimpleShipDisplayInfo<>('s', '*');
        Coordinate c1 = new Coordinate(1, 2);
        assertEquals('s', charDisplayInfo.getInfo(c1, false));
        assertEquals('*', charDisplayInfo.getInfo(c1, true));

        SimpleShipDisplayInfo<Integer> intDisplayInfo = new SimpleShipDisplayInfo<>(0, 1);
        assertEquals(0, intDisplayInfo.getInfo(c1, false));
        assertEquals(1, intDisplayInfo.getInfo(c1, true));
    }
}