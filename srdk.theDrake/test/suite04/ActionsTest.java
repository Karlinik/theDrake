package suite04;

import org.junit.Test;
import srdk.theDrake.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ActionsTest {

	private Set<Move> makeSet(Move ...moves) {
		Set<Move> result = new HashSet<Move>();
		for(Move m : moves) {
			result.add(m);
		}
		
		return result;
	}
	
	@Test
	public void test()  {
		Board board = new Board(4);
		StandardDrakeSetup setup = new StandardDrakeSetup();
		
		BoardTroops blueTroops = new BoardTroops(PlayingSide.BLUE);
		blueTroops = blueTroops
				.placeTroop(setup.DRAKE, board.pos("a1"))
				.placeTroop(setup.CLUBMAN, board.pos("a2"))
				.placeTroop(setup.SPEARMAN, board.pos("b2"));
		Army blueArmy = new Army(blueTroops, Collections.emptyList(), Collections.emptyList());
		
		BoardTroops orangeTroops = new BoardTroops(PlayingSide.ORANGE);
		orangeTroops = orangeTroops
				.placeTroop(setup.DRAKE, board.pos("c4"))
				.placeTroop(setup.MONK, board.pos("c3")) 
				.placeTroop(setup.CLUBMAN, board.pos("b3"));
		Army orangeArmy = new Army(orangeTroops, Collections.emptyList(), Collections.emptyList());
		
		GameState state = new GameState(board, blueArmy, orangeArmy);

		assertEquals(
			makeSet(
				new StepOnly(board.pos("a1"), board.pos("b1")),
				new StepOnly(board.pos("a1"), board.pos("c1")),
				new StepOnly(board.pos("a1"), board.pos("d1"))
			),
			new HashSet<Move>(
				state.tileAt(board.pos("a1")).movesFrom(board.pos("a1"), state)
			)
		);
		
		assertEquals(
				makeSet(
					new StepOnly(board.pos("a2"), board.pos("a3"))
				),
				new HashSet<Move>(
					state.tileAt(board.pos("a2")).movesFrom(board.pos("a2"), state)
				)
			);
		
		assertEquals(
				makeSet(
					new StepAndCapture(board.pos("b2"), board.pos("b3")),
					new CaptureOnly(board.pos("b2"), board.pos("c4"))
				),
				new HashSet<Move>(
					state.tileAt(board.pos("b2")).movesFrom(board.pos("b2"), state)
				)
			);

	
	}
}


