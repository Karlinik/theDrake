package srdk.theDrake;

import java.util.ArrayList;
import java.util.List;

public class ShiftAction extends TroopAction {

	public ShiftAction(Offset2D offset) {
		super(offset);
	}

	public ShiftAction(int offsetX, int offsetY) {
		super(offsetX, offsetY);
	}

	@Override
	public List<Move> movesFrom(Board.Pos origin, PlayingSide side, GameState state) {
		List<Move> result = new ArrayList<>();
		TilePos target = origin.stepByPlayingSide(offset(), side);

		if(state.canStep(origin, target)) {
			result.add(new StepOnly(origin, (Board.Pos)target));
		} else if(state.canCapture(origin, target)) {
			result.add(new StepAndCapture(origin, (Board.Pos)target));
		}

		return result;
	}
}
