package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InBoundsRuleCheckerTest {
    V1ShipFactory factory1 = new V1ShipFactory();
    V2ShipFactory factory2 = new V2ShipFactory();
    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    Board<Character> board = new BattleShipBoard<>(5, 5, checker, 'X');

    @Test
    void test_rule() {
        // Off the board
        Ship<Character> s1 = factory1.makeDestroyer(new Placement("G6V"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s1, board));
        Ship<Character> s10 = factory2.makeBattleship(new Placement("G6D"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s10, board));

        // Top left corner
        Ship<Character> s2 = factory1.makeBattleship(new Placement("A1H"));
        assertNull(checker.checkPlacement(s2, board));
        Ship<Character> s6 = factory1.makeBattleship(new Placement("A1V"));
        assertNull(checker.checkPlacement(s6, board));
        Ship<Character> s11 = factory2.makeBattleship(new Placement("A1D"));
        assertNull(checker.checkPlacement(s11, board));
        Ship<Character> s12 = factory2.makeCarrier(new Placement("A1U"));
        assertNull(checker.checkPlacement(s12, board));

        // Bottom left corner
        Ship<Character> s3 = factory1.makeBattleship(new Placement("E1H"));
        assertNull(checker.checkPlacement(s3, board));
        Ship<Character> s7 = factory1.makeBattleship(new Placement("E1V"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s7, board));
        Ship<Character> s13 = factory2.makeBattleship(new Placement("D1D"));
        assertNull(checker.checkPlacement(s13, board));
        Ship<Character> s14 = factory2.makeCarrier(new Placement("D1U"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s14, board));

        // One square off board
        Ship<Character> s4 = factory1.makeCarrier(new Placement("A1H"));
        assertEquals("That placement is invalid: the ship goes off the right of the board.\n", checker.checkPlacement(s4, board));
        Ship<Character> s5 = factory1.makeCarrier(new Placement("A1V"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s5, board));

        // Off the top
        Ship<Character> s8 = factory1.makeSubmarine(new Placement(new Coordinate(-1, 1), 'V'));
        assertEquals("That placement is invalid: the ship goes off the top of the board.\n", checker.checkPlacement(s8, board));

        // Off the left
        Ship<Character> s9 = factory1.makeSubmarine(new Placement(new Coordinate(1, -1), 'V'));
        assertEquals("That placement is invalid: the ship goes off the left of the board.\n", checker.checkPlacement(s9, board));
    }
}