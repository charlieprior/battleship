package edu.duke.cgp26.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoCollisionRuleCheckerTest {
    @Test
    public void test_rule() {
        V1ShipFactory factory = new V1ShipFactory();
        PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        Board<Character> board = new BattleShipBoard<>(5, 5, checker);

        Ship<Character> s1 = factory.makeBattleship(new Placement("A1H"));
        assertNull(checker.checkPlacement(s1, board)); // Add to empty board
        board.tryAddShip(s1);
        assertEquals("That placement is invalid: the ship overlaps another ship.\n", checker.checkPlacement(s1, board)); // Duplicate

        Ship<Character> s2 = factory.makeBattleship(new Placement("B1H"));
        assertNull(checker.checkPlacement(s2, board));
        board.tryAddShip(s2);

        Ship<Character> s3 = factory.makeDestroyer(new Placement("A1V"));
        assertEquals("That placement is invalid: the ship overlaps another ship.\n", checker.checkPlacement(s3, board)); // Overlaps at A1

        Ship<Character> s4 = factory.makeSubmarine(new Placement("A0H"));
        assertEquals("That placement is invalid: the ship overlaps another ship.\n", checker.checkPlacement(s4, board)); // Overlaps at A1
    }

    @Test
    public void test_combo() {
        V1ShipFactory factory = new V1ShipFactory();
        PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null));
        Board<Character> board = new BattleShipBoard<>(5, 5, checker);

        Ship<Character> s1 = factory.makeBattleship(new Placement("A0H"));
        assertNull(checker.checkPlacement(s1, board)); // in bounds and non-overlapping
        board.tryAddShip(s1);

        Ship<Character> s2 = factory.makeBattleship(new Placement("A3H"));
        assertNotNull(checker.checkPlacement(s1, board)); // out of bounds and overlapping

        Ship<Character> s3 = factory.makeDestroyer(new Placement("A0V"));
        assertNotNull(checker.checkPlacement(s3, board)); // overlapping

        Ship<Character> s4 = factory.makeCarrier(new Placement("B3H")); // out of bounds
        assertNotNull(checker.checkPlacement(s4, board));
    }

}