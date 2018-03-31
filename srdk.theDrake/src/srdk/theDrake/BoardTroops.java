package srdk.theDrake;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable{
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
        if(troopMap.get(pos) == null)
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
        return isLeaderPlaced() && guards < 2;
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
        if(at(target).isPresent())
            throw new IllegalArgumentException();

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        newTroops.put(target, new TroopTile(troop, playingSide, TroopFace.AVERS));

        if(!isLeaderPlaced())
            return new BoardTroops(playingSide, newTroops, target, 0);

        if (isPlacingGuards())
            return new BoardTroops(playingSide, newTroops, leaderPosition, guards+1);

        return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
    }

    public BoardTroops troopStep(Board.Pos origin, Board.Pos target) {
        if(!isLeaderPlaced())
            throw new IllegalStateException ("Cannot move troops before the leader is placed.");

        if(isPlacingGuards())
            throw new IllegalStateException ("Cannot move troops before guards are placed.");

        if(!at(origin).isPresent() || at(target).isPresent())
            throw new IllegalArgumentException();

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(target, tile.flipped());
        if(origin.equals(leaderPosition))
            return new BoardTroops(playingSide(), newTroops, target, guards);
        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops troopFlip(Board.Pos origin) {
        if(!isLeaderPlaced())
            throw new IllegalStateException("Cannot move troops before the leader is placed.");

        if(isPlacingGuards())
            throw new IllegalStateException("Cannot move troops before guards are placed.");

        if(!at(origin).isPresent())
            throw new IllegalArgumentException();

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops removeTroop(Board.Pos target) {
        if(!isLeaderPlaced())
            throw new IllegalStateException("Cannot remove troops before the leader is placed.");

        if(isPlacingGuards())
            throw new IllegalStateException("Cannot remove troops before guards are placed.");

        if(!at(target).isPresent())
            throw new IllegalArgumentException();

        Map<Board.Pos, TroopTile> newTroops = new HashMap<>(troopMap);
        newTroops.remove(target);
        if(leaderPosition.equals(target))
            return new BoardTroops(playingSide(), newTroops, TilePos.OFF_BOARD, guards);
        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.println("\"side\": \"" + this.playingSide() + "\",");
        writer.println("\"leaderPosition\": \"" + this.leaderPosition.i() + this.leaderPosition.j() + "\",");
        writer.println("\"guards\": \"" + this.guards + "\",");
        writer.println("\"troopMap\": {\"");
        int i=1;
        for (Map.Entry<Board.Pos, TroopTile> entry : troopMap.entrySet()){
            if(i>1)
                writer.println(",");
            if (entry.getValue().hasTroop()){
                writer.println("\"" + entry.getKey().i() + entry.getKey().j() + "\": {");
                writer.println("\"troop\": \""+ entry.getValue().troop() + "\",");
                writer.println("\"side\": \""+ entry.getValue().side() + "\",");
                writer.println("\"face\": \"" + entry.getValue().face() + "\"");
                writer.println("}");
            }
            i++;
        }
        writer.println("}");
    }
}
