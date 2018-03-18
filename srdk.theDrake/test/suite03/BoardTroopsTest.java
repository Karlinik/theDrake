package suite03;

import org.junit.Test;
import srdk.theDrake.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class BoardTroopsTest {

    @Test
    public void classStructure() {
        // All attributes private and final
        for(Field f : Board.class.getFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()));
            assertTrue(Modifier.isFinal(f.getModifiers()));
        }
    }

    @Test
    public void placingTroops() {
        Board board = new Board(3);

        // Jednotky na desce začínají prázdné, žádný vůdce,
        // žádné stráže
        BoardTroops troops1 = new BoardTroops(PlayingSide.BLUE);

        assertSame(PlayingSide.BLUE, troops1.playingSide());
        assertSame(TilePos.OFF_BOARD, troops1.leaderPosition());
        assertSame(0, troops1.guards());
        assertSame(false, troops1.isLeaderPlaced());
        assertSame(false, troops1.isPlacingGuards());

        //checkEmpty(board, troops1);


        // Nejdříve postavíme vůdce
        Troop drake = new Troop("Drake");
        BoardTroops troops2 = troops1.placeTroop(drake, board.pos("a2"));
        assertTrue(troops1 != troops2);
        //checkEmpty(board, troops1);

        assertSame(0, troops2.guards());
        assertSame(true, troops2.isLeaderPlaced());
        assertSame(true, troops2.isPlacingGuards());
        assertEquals(Collections.singleton(board.pos("a2")), troops2.troopPositions());

        //assertEquals(Optional.empty(), troops2.at(board.pos("a1")));
        assertSame(drake, troops2.at(board.pos("a2")).get().troop());
        //assertEquals(Optional.empty(), troops2.at(board.pos("a3")));
        //assertEquals(Optional.empty(), troops2.at(board.pos("b1")));
        /*assertEquals(Optional.empty(), troops2.at(board.pos("b2")));
        assertEquals(Optional.empty(), troops2.at(board.pos("b3")));
        assertEquals(Optional.empty(), troops2.at(board.pos("c1")));
        assertEquals(Optional.empty(), troops2.at(board.pos("c2")));
        assertEquals(Optional.empty(), troops2.at(board.pos("c3")));*/

        assertSame(TroopFace.AVERS, troops2.at(board.pos("a2")).get().face());
        assertSame(troops2.playingSide(), troops2.at(board.pos("a2")).get().side());
        assertEquals(board.pos("a2"), troops2.leaderPosition());


        // První stráž
        Troop clubman1 = new Troop("Clubman");
        BoardTroops troops3 = troops2.placeTroop(clubman1, board.pos("a1"));
        assertSame(1, troops3.guards());
        assertSame(true, troops3.isLeaderPlaced());
        assertSame(true, troops3.isPlacingGuards());
        assertEquals(
                new HashSet<Board.Pos>(
                        Arrays.asList(
                                board.pos("a1"),
                                board.pos("a2")
                        )
                ),
                troops3.troopPositions());

        assertSame(clubman1, troops3.at(board.pos("a1")).get().troop());
        assertSame(drake, troops3.at(board.pos("a2")).get().troop());
        //assertEquals(Optional.empty(), troops3.at(board.pos("a3")));
        /*assertEquals(Optional.empty(), troops3.at(board.pos("b1")));
        assertEquals(Optional.empty(), troops3.at(board.pos("b2")));
        assertEquals(Optional.empty(), troops3.at(board.pos("b3")));
        assertEquals(Optional.empty(), troops3.at(board.pos("c1")));
        assertEquals(Optional.empty(), troops3.at(board.pos("c2")));
        assertEquals(Optional.empty(), troops3.at(board.pos("c3")));*/

        // Druhá stráž
        Troop clubman2 = new Troop("Clubman");
        BoardTroops troops4 = troops3.placeTroop(clubman2, board.pos("b2"));
        assertSame(2, troops4.guards());
        assertSame(true, troops4.isLeaderPlaced());
        assertSame(false, troops4.isPlacingGuards());
        assertEquals(
                new HashSet<Board.Pos>(
                        Arrays.asList(
                                board.pos("a1"),
                                board.pos("a2"),
                                board.pos("b2")
                        )
                ),
                troops4.troopPositions());

        assertSame(clubman1, troops4.at(board.pos("a1")).get().troop());
        assertSame(drake, troops4.at(board.pos("a2")).get().troop());
        /*assertEquals(Optional.empty(), troops4.at(board.pos("a3")));
        assertEquals(Optional.empty(), troops4.at(board.pos("b1")));*/
        assertSame(clubman2, troops4.at(board.pos("b2")).get().troop());
        /*assertEquals(Optional.empty(), troops4.at(board.pos("b3")));
        assertEquals(Optional.empty(), troops4.at(board.pos("c1")));
        assertEquals(Optional.empty(), troops4.at(board.pos("c2")));
        assertEquals(Optional.empty(), troops4.at(board.pos("c3")));*/

        // Nějaká další jednotka
        Troop spearman = new Troop("Spearman");
        BoardTroops troops5 = troops4.placeTroop(spearman, board.pos("c1"));
        assertSame(2, troops5.guards());
        assertSame(true, troops5.isLeaderPlaced());
        assertSame(false, troops5.isPlacingGuards());
        assertEquals(
                new HashSet<Board.Pos>(
                        Arrays.asList(
                                board.pos("a1"),
                                board.pos("a2"),
                                board.pos("b2"),
                                board.pos("c1")
                        )
                ),
                troops5.troopPositions());

        assertSame(clubman1, troops5.at(board.pos("a1")).get().troop());
        assertSame(drake, troops5.at(board.pos("a2")).get().troop());
        /*assertEquals(Optional.empty(), troops5.at(board.pos("a3")));
        assertEquals(Optional.empty(), troops5.at(board.pos("b1")));*/
        assertSame(clubman2, troops5.at(board.pos("b2")).get().troop());
        //assertEquals(Optional.empty(), troops5.at(board.pos("b3"))
        assertSame(spearman, troops5.at(board.pos("c1")).get().troop());
        /*assertEquals(Optional.empty(), troops5.at(board.pos("c2")));
        assertEquals(Optional.empty(), troops5.at(board.pos("c3")));*/
    }

    @Test
    public void movingTroops() {
        Board board = new Board(3);
        BoardTroops troops = new BoardTroops(PlayingSide.ORANGE);
        assertSame(PlayingSide.ORANGE, troops.playingSide());

        Troop drake = new Troop("Clubman");
        Troop clubman1 = new Troop("Clubman");
        Troop clubman2 = new Troop("Clubman");
        Troop spearman = new Troop("Spearman");

        try {
            troops.troopStep(board.pos("a2"), board.pos("a3"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(drake, board.pos("a2"));

        try {
            troops.troopStep(board.pos("a2"), board.pos("a3"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(clubman1, board.pos("a1"));

        try {
            troops.troopStep(board.pos("a2"), board.pos("a3"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }


        troops = troops
                .placeTroop(clubman2, board.pos("b2"))
                .placeTroop(spearman, board.pos("c1"));

        assertSame(clubman1, troops.at(board.pos("a1")).get().troop());
        assertSame(drake, troops.at(board.pos("a2")).get().troop());
        /*assertEquals(Optional.empty(), troops.at(board.pos("a3")));
        assertEquals(Optional.empty(), troops.at(board.pos("b1")));*/
        assertSame(clubman2, troops.at(board.pos("b2")).get().troop());
        //assertEquals(Optional.empty(), troops.at(board.pos("b3")));
        assertSame(spearman, troops.at(board.pos("c1")).get().troop());
        /*assertEquals(Optional.empty(), troops.at(board.pos("c2")));
        assertEquals(Optional.empty(), troops.at(board.pos("c3")));*/

        troops = troops.troopStep(board.pos("a2"), board.pos("a3"));

        assertSame(clubman1, troops.at(board.pos("a1")).get().troop());
        //assertEquals(Optional.empty(), troops.at(board.pos("a2")));
        assertSame(drake, troops.at(board.pos("a3")).get().troop());
        //assertEquals(Optional.empty(), troops.at(board.pos("b1")));
        assertSame(clubman2, troops.at(board.pos("b2")).get().troop());
        assertSame(drake, troops.at(board.pos("a3")).get().troop());
        //assertEquals(Optional.empty(), troops.at(board.pos("b3")));
        assertSame(spearman, troops.at(board.pos("c1")).get().troop());
        //assertEquals(Optional.empty(), troops.at(board.pos("c2")));
        //assertEquals(Optional.empty(), troops.at(board.pos("c3")));

        // Pozor na pozici vůdce
        //assertEquals(board.pos("a3"), troops.leaderPosition());

        /*try {
            troops.troopStep(board.pos("a2"), board.pos("c2"));
            fail();
        } catch(IllegalArgumentException e) {
            // Není možné se pohnout z prázného políčka
        }*/

        /*try {
            troops.troopStep(board.pos("c1"), board.pos("a3"));
            fail();
        } catch(IllegalArgumentException e) {
            // Není možné se pohnout na políčko s jednotkou
        }*/
    }

    @Test
    public void flippingTroops() {
        Board board = new Board(3);
        BoardTroops troops = new BoardTroops(PlayingSide.ORANGE);
        assertSame(PlayingSide.ORANGE, troops.playingSide());

        Troop drake = new Troop("Clubman");
        Troop clubman1 = new Troop("Clubman");
        Troop clubman2 = new Troop("Clubman");

        try {
            troops.troopFlip(board.pos("a2"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(drake, board.pos("a2"));

        try {
            troops.troopFlip(board.pos("a2"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(clubman1, board.pos("a1"));

        try {
            troops.troopFlip(board.pos("a2"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }


        troops = troops
                .placeTroop(clubman2, board.pos("b2"))
                .troopFlip(board.pos("a2"));

        assertSame(drake, troops.at(board.pos("a2")).get().troop());
        assertSame(TroopFace.REVERS, troops.at(board.pos("a2")).get().face());

        /*try {
            troops.troopFlip(board.pos("a3"));
            fail();
        } catch(IllegalArgumentException e) {
            // Není možné otočit jednotku na prázdném políčku
        }
        */
    }

    @Test
    public void removingTroops() {
        Board board = new Board(3);
        BoardTroops troops = new BoardTroops(PlayingSide.ORANGE);
        assertSame(PlayingSide.ORANGE, troops.playingSide());

        Troop drake = new Troop("Clubman");
        Troop clubman1 = new Troop("Clubman");
        Troop clubman2 = new Troop("Clubman");

        try {
            troops.removeTroop(board.pos("a2"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(drake, board.pos("a2"));

        try {
            troops.removeTroop(board.pos("a2"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(clubman1, board.pos("a1"));

        try {
            troops.removeTroop(board.pos("a2"));
            fail();
        } catch(IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }


        troops = troops.placeTroop(clubman2, board.pos("b2"));

        /*try {
            troops.removeTroop(board.pos("a3"));
            fail();
        } catch(IllegalArgumentException e) {
            // Není možné odstranit jednotku na prázdném políčku
        }*/

        troops = troops.removeTroop(board.pos("a2"));

       // assertSame(Optional.empty(), troops.at(board.pos("a2")));
        //assertSame(TilePos.OFF_BOARD, troops.leaderPosition());
        //assertFalse(troops.isLeaderPlaced());
    }

    /*private void checkEmpty(Board board, BoardTroops boardTroops) {
        assertEquals(Optional.empty(), boardTroops.at(board.pos("a1")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("a2")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("a3")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("b1")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("b2")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("b3")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("c1")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("c2")));
        assertEquals(Optional.empty(), boardTroops.at(board.pos("c3")));

        assertEquals(Collections.emptySet(), boardTroops.troopPositions());
    }*/

}

