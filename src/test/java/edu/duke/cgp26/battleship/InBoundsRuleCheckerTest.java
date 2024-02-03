package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InBoundsRuleCheckerTest {
    V1ShipFactory factory = new V1ShipFactory();
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    Board<Character> board = new BattleShipBoard<>(5, 5, checker);

    @Test
    void test_rule() {
        // Off the board
        Ship<Character> s1 = factory.makeDestroyer(new Placement("G6V"));
        assertFalse(checker.checkPlacement(s1, board));

        // Top left corner
        Ship<Character> s2 = factory.makeBattleship(new Placement("A1H"));
        assertTrue(checker.checkPlacement(s2, board));
        Ship<Character> s6 = factory.makeBattleship(new Placement("A1V"));
        assertTrue(checker.checkPlacement(s6, board));

        // Bottom left corner
        Ship<Character> s3 = factory.makeBattleship(new Placement("E1H"));
        assertTrue(checker.checkPlacement(s3, board));
        Ship<Character> s7 = factory.makeBattleship(new Placement("E1V"));
        assertFalse(checker.checkPlacement(s7, board));

        // One square off board
        Ship<Character> s4 = factory.makeCarrier(new Placement("A1H"));
        assertFalse(checker.checkPlacement(s4, board));
        Ship<Character> s5 = factory.makeCarrier(new Placement("A1V"));
        assertFalse(checker.checkPlacement(s5, board));
    }
}