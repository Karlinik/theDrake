package srdk.theDrake;

import java.util.Collections;
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
		TilePos target = origin.stepByPlayingSide(offset(), side);

		if(!target.equals(TilePos.OFF_BOARD)){
			if(state.canCapture(origin, target)) {
				return Collections.singletonList(new StepAndCapture(origin, (Board.Pos)target));
			} else if(state.canStep(origin, target)) {
				return Collections.singletonList(new StepOnly(origin, (Board.Pos)target));
			}
		}

		return Collections.emptyList();
	}
}
