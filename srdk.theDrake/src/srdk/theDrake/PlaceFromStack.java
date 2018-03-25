package srdk.theDrake;

public class PlaceFromStack extends Move {

    public PlaceFromStack(Board.Pos target) {
        super(target);
    }

    @Override
    public GameState execute(GameState originState) {
        return originState.placeFromStack(target());
    }

}
