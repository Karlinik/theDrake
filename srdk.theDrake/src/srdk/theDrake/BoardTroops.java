package srdk.theDrake;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BoardTroops {
    private final PlayingSide playingSide;
    private final Map<Board.Pos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private final int guards;

    public BoardTroops(
            PlayingSide playingSide,
            Map<Board.Pos, TroopTile> troopMap,
            TilePos leaderPosition,
            int guards) {
        this.playingSide = playingSide;
        this.troopMap= troopMap;
        this.leaderPosition = leaderPosition;
        this.guards = guards;
    }

    public BoardTroops(PlayingSide playingSide) {
        this(playingSide, new Map<Board.Pos, TroopTile>(), TilePos.OFF_BOARD, 0);
    }


    public Optional<TroopTile> at(TilePos pos) {
        // Místo pro váš kód
    }

    public PlayingSide playingSide() {
        // Místo pro váš kód
    }

    public TilePos leaderPosition() {
        // Místo pro váš kód
    }

    public int guards() {
        // Místo pro váš kód
    }

    public boolean isLeaderPlaced() {
        // Místo pro váš kód
    }

    public boolean isPlacingGuards() {
        // Místo pro váš kód
    }

    public Set<Board.Pos> troopPositions() {
        // Místo pro váš kód
    }

    public BoardTroops placeTroop(Troop troop, Board.Pos target) {
        // Místo pro váš kód
    }

    public BoardTroops troopStep(Board.Pos origin, Board.Pos target) {
        // Místo pro váš kód
    }

    public BoardTroops troopFlip(Board.Pos origin) {
        if(!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if(isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if(!at(origin).isPresent())
            throw new IllegalArgumentException();

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops removeTroop(Board.Pos target) {
        // Místo pro váš kód
    }
}
