package srdk.theDrake;

import java.util.Collections;
import java.util.List;

/**
 * Created by Nikola Karlikova on 19.03.2018.
 */
public class StrikeAction extends TroopAction {
    public StrikeAction(Offset2D offset) {
        super(offset);
    }

    public StrikeAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    @Override
    public List<Move> movesFrom(Board.Pos origin, PlayingSide side, GameState state) {
        TilePos target = origin.stepByPlayingSide(offset(), side);

        if(!target.equals(TilePos.OFF_BOARD) && state.canStep(origin, target) && state.canCapture(origin, target))
            return Collections.singletonList(new CaptureOnly(origin, (Board.Pos)target));

        return Collections.emptyList();
    }
}
