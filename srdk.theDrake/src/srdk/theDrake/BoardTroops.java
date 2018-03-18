package srdk.theDrake;

import java.util.*;

public class BoardTroops {
    private final PlayingSide playingSide;
    private final Map<Board.Pos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private int guards;

    public BoardTroops(
            PlayingSide playingSide,
            Map<Board.Pos, TroopTile> troopMap,
            TilePos leaderPosition,
            int guards) {
        this.playingSide = playingSide;
        this.troopMap = troopMap;
        this.leaderPosition = leaderPosition;
        this.guards = guards;
    }

    public BoardTroops(PlayingSide playingSide) {
        this(playingSide, Collections.emptyMap(), TilePos.OFF_BOARD, 0);
    }


    public Optional<TroopTile> at(TilePos pos) {
        //TBD - vraceni prazdne
        if(troopMap.get(pos).equals(null))
            return Optional.empty();
        return Optional.of(troopMap.get(pos));
    }

    public PlayingSide playingSide() {
        return playingSide;
    }

    public TilePos leaderPosition() {
        return leaderPosition;
    }

    public int guards() {
        return guards;
    }

    public boolean isLeaderPlaced() {
        return !leaderPosition.equals(TilePos.OFF_BOARD);
    }

    public boolean isPlacingGuards() {
        return isLeaderPlaced() && guards<3;
    }

    public Set<Board.Pos> troopPositions() {
        Set<Board.Pos> troopPositions = new HashSet<>();
        for (Map.Entry<Board.Pos, TroopTile> entry : troopMap.entrySet())
        {
            if (entry.getValue().hasTroop())
                troopPositions.add(entry.getKey());
        }
        return troopPositions;
    }

    public BoardTroops placeTroop(Troop troop, Board.Pos target) {
        //if(at(target).isPresent())
          //  throw new IllegalArgumentException();
        //Dokud se neopravi metoda at ... jinak to porad hazi vyjimku

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        newTroops.put(target, new TroopTile(troop, playingSide, TroopFace.AVERS));

        if(!isLeaderPlaced())
            return new BoardTroops(playingSide, newTroops, target, guards);

        if (isPlacingGuards())
            return new BoardTroops(playingSide, newTroops, leaderPosition, guards+1);

        return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
    }

    public BoardTroops troopStep(Board.Pos origin, Board.Pos target) {
        if(isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }
        if(isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }
        if(!at(origin).isPresent() || at(target).isPresent())
            throw new IllegalArgumentException();

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        newTroops.remove(target);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(target, tile.flipped());
        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
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
        if(!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if(isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if(!at(target).isPresent())
            throw new IllegalArgumentException();

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        newTroops.remove(target);
        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }
}
