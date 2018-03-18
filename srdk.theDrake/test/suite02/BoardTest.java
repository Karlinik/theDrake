package suite02;

import org.junit.Test;
import srdk.theDrake.Board;
import srdk.theDrake.BoardTile;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class BoardTest {

	@Test
	public void classStructure() {
		// All attributes private and final
		for(Field f : Board.class.getFields()) {
			assertTrue(Modifier.isPrivate(f.getModifiers()));
			assertTrue(Modifier.isFinal(f.getModifiers()));
		}
	}
	
	@Test
	public void behaviour() {
		Board emptyBoard = new Board(3);
		assertSame(3, emptyBoard.dimension());
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("a1")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("b1")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("c1")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("a2")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("b2")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("c2")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("a3")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("b3")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("c3")));
		
		Board board = emptyBoard.withTiles(
			new Board.TileAt(emptyBoard.pos(0, 0), BoardTile.MOUNTAIN),
			new Board.TileAt(emptyBoard.pos(1, 2), BoardTile.MOUNTAIN),
			new Board.TileAt(emptyBoard.pos(2, 1), BoardTile.MOUNTAIN)
		);
		assertSame(3, board.dimension());
		assertSame(BoardTile.MOUNTAIN, board.at(board.pos("a1")));
		assertSame(BoardTile.EMPTY, board.at(board.pos("b1")));
		assertSame(BoardTile.EMPTY, board.at(board.pos("c1")));
		assertSame(BoardTile.EMPTY, board.at(board.pos("a2")));
		assertSame(BoardTile.EMPTY, board.at(board.pos("b2")));
		assertSame(BoardTile.MOUNTAIN, board.at(board.pos("c2")));
		assertSame(BoardTile.EMPTY, board.at(board.pos("a3")));
		assertSame(BoardTile.MOUNTAIN, board.at(board.pos("b3")));
		assertSame(BoardTile.EMPTY, board.at(board.pos("c3")));
		
		//Test that the emptyBoard stays empty end independent 
		//of the new board.
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("a1")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("b1")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("c1")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("a2")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("b2")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("c2")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("a3")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("b3")));
		assertSame(BoardTile.EMPTY, emptyBoard.at(emptyBoard.pos("c3")));
	}
}
