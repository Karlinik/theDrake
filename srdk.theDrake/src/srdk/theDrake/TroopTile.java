package srdk.theDrake;

import java.util.List;

public class TroopTile implements Tile{

    private Troop troop;
    private PlayingSide side;
    private TroopFace face;

    public TroopTile(Troop troop, PlayingSide side, TroopFace face){
        this.troop = troop;
        this.side = side;
        this.face = face;
    }

    //Getters
    public Troop troop(){ return troop; }

    public PlayingSide side(){
        return side;
    }

    public TroopFace face(){
        return face;
    }

    // Vytvoří novou dlaždici, s jednotkou otočenou na opačnou stranu
    // (z rubu na líc nebo z líce na rub)
    public TroopTile flipped(){
        return new TroopTile(troop, side, face.flipped());
    }

    @Override
    public boolean canStepOn() {
        return false;
    }

    @Override
    public boolean hasTroop() {
        return troop != null;

    }

    @Override
    public List<Move> movesFrom(Board.Pos pos, GameState state) {
        return null;
    }
}
