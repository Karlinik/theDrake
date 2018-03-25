package srdk.theDrake;

import java.util.List;

public interface Tile {
    public boolean canStepOn();
    public boolean hasTroop();
    public List<Move> movesFrom(Board.Pos pos, GameState state);
}
