package srdk.theDrake;

public class StepAndCapture extends BoardMove {

    public StepAndCapture(Board.Pos origin, Board.Pos target) {
        super(origin, target);
    }

    @Override
    public GameState execute(GameState originState) {
        return originState.stepAndCapture(origin(), target());
    }

}
