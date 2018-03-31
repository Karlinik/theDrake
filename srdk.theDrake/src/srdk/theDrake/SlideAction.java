package srdk.theDrake;

import java.util.ArrayList;
import java.util.List;

public class SlideAction extends TroopAction {

    public SlideAction(Offset2D offset) {
        super(offset);
    }

    public SlideAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    @Override
    public List<Move> movesFrom(Board.Pos origin, PlayingSide side, GameState state) {
        List<Move> result = new ArrayList<>();
        TilePos target = origin.stepByPlayingSide(offset(), side);

        while(!target.equals(TilePos.OFF_BOARD) && state.canStep(origin, target)) {
            if(state.canCapture(origin, target))
                result.add(new StepAndCapture(origin, (Board.Pos)target));
            result.add(new StepOnly(origin, (Board.Pos)target));
            target = target.stepByPlayingSide(offset(), side);
        }

        return result;
    }
}