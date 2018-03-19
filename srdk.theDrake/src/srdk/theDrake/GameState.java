package srdk.theDrake;

import java.util.Optional;

public class GameState{
	private final Board board;
	private final PlayingSide sideOnTurn;
	private final Army blueArmy;
	private final Army orangeArmy;
	private final GameResult result;
	
	public GameState(
			Board board, 
			Army blueArmy, 
			Army orangeArmy) {
		this(board, blueArmy, orangeArmy, PlayingSide.BLUE, GameResult.IN_PLAY);
	}
	
	public GameState(
			Board board, 
			Army blueArmy, 
			Army orangeArmy, 
			PlayingSide sideOnTurn, 
			GameResult result) {
		this.board = board;
		this.sideOnTurn = sideOnTurn;
		this.blueArmy = blueArmy;
		this.orangeArmy = orangeArmy;
		this.result = result;
	}
	
	public Board board() {
		return board;
	}
	
	public PlayingSide sideOnTurn() {
		return sideOnTurn;
	}
	
	public GameResult result() {
		return result;
	}
	
	public Army army(PlayingSide side) {
		if(side == PlayingSide.BLUE) {
			return blueArmy;
		}
		return orangeArmy;
	}
	
	public Army armyOnTurn() {
		return army(sideOnTurn);
	}
	
	public Army armyNotOnTurn() {
		if(sideOnTurn == PlayingSide.BLUE)
			return orangeArmy;
		
		return blueArmy;
	}
	
	public Tile tileAt(Board.Pos pos) {
		if(!blueArmy.boardTroops().at(pos).isPresent() && !orangeArmy.boardTroops().at(pos).isPresent())
			return board.at(pos);
		throw new IllegalArgumentException();
	}
	
	private boolean canStepFrom(TilePos origin) {
		if(!result.equals(result.IN_PLAY))
			return false;

		if(!blueArmy.boardTroops().at(origin).isPresent() && !orangeArmy.boardTroops().at(origin).isPresent())
			return false;

		if(!blueArmy.boardTroops().isLeaderPlaced() || blueArmy.boardTroops().isPlacingGuards())
			return false;

		if(blueArmy.boardTroops().at(origin).isPresent() && sideOnTurn.equals(sideOnTurn.ORANGE))
			return false;

		if(orangeArmy.boardTroops().at(origin).isPresent() && sideOnTurn.equals(sideOnTurn.BLUE))
			return false;

		return true;
	}

	private boolean canStepTo(TilePos target) {
		if(result!=(result.IN_PLAY))
			return false;

		if(sideOnTurn.equals(PlayingSide.BLUE)){
			if(blueArmy.boardTroops().at(target).isPresent())
				return false;
			if(orangeArmy.boardTroops().at(target).isPresent() && orangeArmy.boardTroops().at(target).get().canStepOn())
				return true;
		}

		if(sideOnTurn.equals(PlayingSide.ORANGE)) {
			if (orangeArmy.boardTroops().at(target).isPresent())
				return false;
			if (blueArmy.boardTroops().at(target).isPresent() && blueArmy.boardTroops().at(target).get().canStepOn())
				return true;
		}
		return true;
	}
	
	private boolean canCaptureOn(TilePos target) {
		if(result != result.IN_PLAY)
			return false;

		if(sideOnTurn.equals(PlayingSide.BLUE))
			if(!orangeArmy.boardTroops().at(target).isPresent())
				return false;

		if(sideOnTurn.equals(PlayingSide.ORANGE))
			if(!blueArmy.boardTroops().at(target).isPresent())
				return false;

		return true;
	}
	
	public boolean canStep(TilePos origin, TilePos target)  {
		return canStepFrom(origin) && canStepTo(target);
	}
	
	public boolean canCapture(TilePos origin, TilePos target)  {
		return canStepFrom(origin) && canCaptureOn(target);
	}
	
	public boolean canPlaceFromStack(TilePos target) {
		if(result != result.IN_PLAY)
			return false;
		if(sideOnTurn.equals(PlayingSide.BLUE))
			if(!orangeArmy.boardTroops().at(target).isPresent())
				return false;
	}
	
	public GameState stepOnly(Board.Pos origin, Board.Pos target) {		
		if(canStep(origin, target))		 
			return createNewGameState(
					armyNotOnTurn(),
					armyOnTurn().troopStep(origin, target), GameResult.IN_PLAY);
		
		throw new IllegalArgumentException();
	}
	
	public GameState stepAndCapture(Board.Pos origin, Board.Pos target) {
		if(canCapture(origin, target)) {
			Troop captured = armyNotOnTurn().boardTroops().at(target).get().troop();
			GameResult newResult = GameResult.IN_PLAY;
			
			if(armyNotOnTurn().boardTroops().leaderPosition().equals(target))
				newResult = GameResult.VICTORY;
			
			return createNewGameState(
					armyNotOnTurn().removeTroop(target), 
					armyOnTurn().troopStep(origin, target).capture(captured), newResult);
		}
		
		throw new IllegalArgumentException();
	}
	
	public GameState captureOnly(Board.Pos origin, Board.Pos target) {
		if(canCapture(origin, target)) {
			Troop captured = armyNotOnTurn().boardTroops().at(target).get().troop();
			GameResult newResult = GameResult.IN_PLAY;
			
			if(armyNotOnTurn().boardTroops().leaderPosition().equals(target))
				newResult = GameResult.VICTORY;
			
			return createNewGameState(
					armyNotOnTurn().removeTroop(target),
					armyOnTurn().troopFlip(origin).capture(captured), newResult);
		}
		
		throw new IllegalArgumentException();
	}
	
	public GameState placeFromStack(Board.Pos target) {
		if(canPlaceFromStack(target)) {
			return createNewGameState(
					armyNotOnTurn(), 
					armyOnTurn().placeFromStack(target), 
					GameResult.IN_PLAY);
		}
		
		throw new IllegalArgumentException();
	}
	
	public GameState resign() {
		return createNewGameState(
				armyNotOnTurn(), 
				armyOnTurn(), 
				GameResult.VICTORY);
	}
	
	public GameState draw() {
		return createNewGameState(
				armyOnTurn(), 
				armyNotOnTurn(), 
				GameResult.DRAW);
	}
	
	private GameState createNewGameState(Army armyOnTurn, Army armyNotOnTurn, GameResult result) {
		if(armyOnTurn.side() == PlayingSide.BLUE) {
			return new GameState(board, armyOnTurn, armyNotOnTurn, PlayingSide.BLUE, result);
		}
		
		return new GameState(board, armyNotOnTurn, armyOnTurn, PlayingSide.ORANGE, result); 
	}
}
