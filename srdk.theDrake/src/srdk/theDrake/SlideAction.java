package srdk.theDrake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikola Karlikova on 19.03.2018.
 */
public class SlideAction extends TroopAction{
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

        if(state.can)
        /*if(state.canStep(origin, target)) {
            result.add(new StepOnly(origin, (Board.Pos)target));
        } else if(state.canCapture(origin, target)) {
            result.add(new StepAndCapture(origin, (Board.Pos)target));
        }

        return result;*/
    }
}
