package suite04;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;

import org.junit.Test;

import srdk.theDrake.Army;
import srdk.theDrake.Board;
import srdk.theDrake.BoardTile;
import srdk.theDrake.GameState;
import srdk.theDrake.PlayingSide;
import srdk.theDrake.StandardDrakeSetup;
import srdk.theDrake.TilePos;

public class GameStateTest {

	private GameState createTestState()  {
		Board board = new Board(3);
		board = board.withTiles(new Board.TileAt(board.pos("a3"), BoardTile.MOUNTAIN));
		return new StandardDrakeSetup().startState(board);
	}
	
	@Test
	public void classStructure() {
		// All attributes private and final
		for(Field f : GameState.class.getFields()) {
			assertTrue(Modifier.isPrivate(f.getModifiers()));
			assertTrue(Modifier.isFinal(f.getModifiers()));
		}
	}
	
	@Test
	public void introGame() {
		GameState state = createTestState();
		
		assertFalse(state.canPlaceFromStack(TilePos.OFF_BOARD));
		
		// Placing the blue leader
		assertTrue(state.canPlaceFromStack(state.board().pos("a1")));
		assertTrue(state.canPlaceFromStack(state.board().pos("b1")));
		assertTrue(state.canPlaceFromStack(state.board().pos("c1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c3")));
		
		state = state.placeFromStack(state.board().pos("a1"));
		
		// Placing the orange leader
		assertFalse(state.canPlaceFromStack(state.board().pos("a1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertTrue(state.canPlaceFromStack(state.board().pos("b3")));
		assertTrue(state.canPlaceFromStack(state.board().pos("c3")));
		
		state = state.placeFromStack(state.board().pos("c3"));
		
		// Placing first blue guard
		assertFalse(state.canPlaceFromStack(state.board().pos("a1")));
		assertTrue(state.canPlaceFromStack(state.board().pos("b1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c1")));
		assertTrue(state.canPlaceFromStack(state.board().pos("a2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c3")));
		
		// No steps or capturing before guards are placed
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("a2")));
		assertFalse(state.canCapture(state.board().pos("a1"), state.board().pos("c3")));
		
		state = state.placeFromStack(state.board().pos("a2"));
		
		// Placing first orange guard
		assertFalse(state.canPlaceFromStack(state.board().pos("a1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b2")));
		assertTrue(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertTrue(state.canPlaceFromStack(state.board().pos("b3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c3")));
		
		// No steps or capturing before guards are placed
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("c2")));
		assertFalse(state.canCapture(state.board().pos("c3"), state.board().pos("a1")));
		
		state = state.placeFromStack(state.board().pos("b3"));
		
		// Placing second blue guard
		assertFalse(state.canPlaceFromStack(state.board().pos("a1")));
		assertTrue(state.canPlaceFromStack(state.board().pos("b1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c3")));
			
		state = state.placeFromStack(state.board().pos("b1"));
		
		// Placing second orange guard
		assertFalse(state.canPlaceFromStack(state.board().pos("a1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b2")));
		assertTrue(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c3")));
			
		state = state.placeFromStack(state.board().pos("c2"));
	}
	
	@Test
	public void middleGameBlue() {
		GameState state = createTestState();
		state = state
				.placeFromStack(state.board().pos("a1"))		
				.placeFromStack(state.board().pos("c3"))
				.placeFromStack(state.board().pos("a2"))
				.placeFromStack(state.board().pos("b3"))
				.placeFromStack(state.board().pos("b1"))
				.placeFromStack(state.board().pos("c2"));
		
		// Placing blue troop
		assertFalse(state.canPlaceFromStack(state.board().pos("a1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b1")));
		assertTrue(state.canPlaceFromStack(state.board().pos("c1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a2")));
		assertTrue(state.canPlaceFromStack(state.board().pos("b2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c3")));
		
		// Stepping with blue troop
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("a3")));
		assertTrue(state.canStep(state.board().pos("a1"), state.board().pos("b2")));
		assertTrue(state.canStep(state.board().pos("a1"), state.board().pos("c1")));
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("a1")));
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("a2")));
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("b1")));
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("b3")));
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("c2")));
		assertFalse(state.canStep(state.board().pos("a1"), state.board().pos("c3")));
		
		// Capturing with blue troop
		assertFalse(state.canCapture(state.board().pos("a1"), state.board().pos("a3")));
		assertFalse(state.canCapture(state.board().pos("a1"), state.board().pos("b2")));
		assertFalse(state.canCapture(state.board().pos("a1"), state.board().pos("c1")));
		assertFalse(state.canCapture(state.board().pos("a1"), state.board().pos("a1")));
		assertFalse(state.canCapture(state.board().pos("a1"), state.board().pos("a2")));
		assertFalse(state.canCapture(state.board().pos("a1"), state.board().pos("b1")));
		assertTrue(state.canCapture(state.board().pos("a1"), state.board().pos("b3")));
		assertTrue(state.canCapture(state.board().pos("a1"), state.board().pos("c2")));
		assertTrue(state.canCapture(state.board().pos("a1"), state.board().pos("c3")));
		
		// Boundaries
		assertFalse(state.canStep(TilePos.OFF_BOARD, state.board().pos("b2")));
		assertFalse(state.canStep(state.board().pos("a1"), TilePos.OFF_BOARD));
		
		assertFalse(state.canCapture(TilePos.OFF_BOARD, state.board().pos("c3")));
		assertFalse(state.canCapture(state.board().pos("a1"), TilePos.OFF_BOARD));
	}
	
	@Test
	public void middleGameOrange() {
		GameState state = createTestState();
		state = state
				.placeFromStack(state.board().pos("a1"))		
				.placeFromStack(state.board().pos("c3"))
				.placeFromStack(state.board().pos("a2"))
				.placeFromStack(state.board().pos("b3"))
				.placeFromStack(state.board().pos("b1"))
				.placeFromStack(state.board().pos("c2"))
				.placeFromStack(state.board().pos("b2"));
		
		// Placing orange troop
		assertFalse(state.canPlaceFromStack(state.board().pos("a1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b1")));
		assertTrue(state.canPlaceFromStack(state.board().pos("c1")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c2")));
		assertFalse(state.canPlaceFromStack(state.board().pos("a3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("b3")));
		assertFalse(state.canPlaceFromStack(state.board().pos("c3")));
		
		// Stepping with orange troop
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("a3")));
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("b2")));
		assertTrue(state.canStep(state.board().pos("c3"), state.board().pos("c1")));
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("a1")));
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("a2")));
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("b1")));
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("b3")));
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("c2")));
		assertFalse(state.canStep(state.board().pos("c3"), state.board().pos("c3")));
		
		// Capturing with orange troop
		assertFalse(state.canCapture(state.board().pos("c3"), state.board().pos("a3")));
		assertTrue(state.canCapture(state.board().pos("c3"), state.board().pos("b2")));
		assertFalse(state.canCapture(state.board().pos("c3"), state.board().pos("c1")));
		assertTrue(state.canCapture(state.board().pos("c3"), state.board().pos("a1")));
		assertTrue(state.canCapture(state.board().pos("c3"), state.board().pos("a2")));
		assertTrue(state.canCapture(state.board().pos("c3"), state.board().pos("b1")));
		assertFalse(state.canCapture(state.board().pos("c3"), state.board().pos("b3")));
		assertFalse(state.canCapture(state.board().pos("c3"), state.board().pos("c2")));
		assertFalse(state.canCapture(state.board().pos("c3"), state.board().pos("c3")));
	}
	
	@Test
	public void emptyStack() {
		Board board = new Board(3);
		GameState state = new GameState(
			board,
			new Army(PlayingSide.BLUE, Collections.emptyList()),
			new Army(PlayingSide.ORANGE, Collections.emptyList())
		);
		
		//No placing from an empty stack
		assertFalse(state.canPlaceFromStack(board.pos("a1")));
		assertFalse(state.canPlaceFromStack(board.pos("a1")));
	}
}



