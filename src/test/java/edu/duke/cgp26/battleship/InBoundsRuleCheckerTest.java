package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InBoundsRuleCheckerTest {
    V1ShipFactory factory = new V1ShipFactory();
    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    Board<Character> board = new BattleShipBoard<>(5, 5, checker);

    @Test
    void test_rule() {
        // Off the board
        Ship<Character> s1 = factory.makeDestroyer(new Placement("G6V"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s1, board));

        // Top left corner
        Ship<Character> s2 = factory.makeBattleship(new Placement("A1H"));
        assertNull(checker.checkPlacement(s2, board));
        Ship<Character> s6 = factory.makeBattleship(new Placement("A1V"));
        assertNull(checker.checkPlacement(s6, board));

        // Bottom left corner
        Ship<Character> s3 = factory.makeBattleship(new Placement("E1H"));
        assertNull(checker.checkPlacement(s3, board));
        Ship<Character> s7 = factory.makeBattleship(new Placement("E1V"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s7, board));

        // One square off board
        Ship<Character> s4 = factory.makeCarrier(new Placement("A1H"));
        assertEquals("That placement is invalid: the ship goes off the right of the board.\n", checker.checkPlacement(s4, board));
        Ship<Character> s5 = factory.makeCarrier(new Placement("A1V"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n", checker.checkPlacement(s5, board));

        // Off the top
        Ship<Character> s8 = factory.makeSubmarine(new Placement(new Coordinate(-1, 1), 'V'));
        assertEquals("That placement is invalid: the ship goes off the top of the board.\n", checker.checkPlacement(s8, board));

        // Off the left
        Ship<Character> s9 = factory.makeSubmarine(new Placement(new Coordinate(1, -1), 'V'));
        assertEquals("That placement is invalid: the ship goes off the left of the board.\n", checker.checkPlacement(s9, board));
    }
}