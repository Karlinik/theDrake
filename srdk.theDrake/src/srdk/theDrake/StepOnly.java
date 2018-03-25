package srdk.theDrake;

public class StepOnly extends BoardMove {

    public StepOnly(Board.Pos origin, Board.Pos target) {
        super(origin, target);
    }

    @Override
    public GameState execute(GameState originState) {
        return originState.stepOnly(origin(), target());
    }

}
