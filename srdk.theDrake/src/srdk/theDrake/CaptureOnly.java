package srdk.theDrake;

public class CaptureOnly extends BoardMove {

    public CaptureOnly(Board.Pos origin, Board.Pos target) {
        super(origin, target);
    }

    @Override
    public GameState execute(GameState originState) {
        return originState.captureOnly(origin(), target());
    }

}
