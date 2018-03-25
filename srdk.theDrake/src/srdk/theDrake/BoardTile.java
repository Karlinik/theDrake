package srdk.theDrake;

import java.util.Collections;
import java.util.List;

public interface BoardTile extends Tile {
    public static BoardTile EMPTY = new BoardTile() {

        @Override
        public boolean canStepOn() {
            return true;
        }

        @Override
        public boolean hasTroop() {
            return false;
        }
    };

    public static final BoardTile MOUNTAIN = new BoardTile() {
        @Override
        public boolean canStepOn() {
            return false;
        }

        @Override
        public boolean hasTroop() {
            return false;
        }
    };

    @Override
    default List<Move> movesFrom(Board.Pos pos, GameState state) {
        return Collections.emptyList();
    }
}
