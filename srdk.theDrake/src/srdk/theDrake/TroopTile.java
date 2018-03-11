package srdk.theDrake;

public class TroopTile implements Tile{

    private Troop troop;
    private PlayingSide side;
    private TroopFace face;

    public TroopTile(Troop troop, PlayingSide side, TroopFace face){
        this.troop = troop;
        this.face = face;
        this.side = face;
    }

    // Vrací barvu, za kterou hraje jednotka na této dlaždici
    public PlayingSide side(){
        return side;
    }

    // Vrací stranu, na kterou je jednotka otočena
    public TroopFace face(){
        return face;
    }

    // Jednotka, která stojí na této dlaždici
    public Troop troop(){
        return troop;
    }

    // Vytvoří novou dlaždici, s jednotkou otočenou na opačnou stranu
    // (z rubu na líc nebo z líce na rub)
    public TroopTile flipped(){
        newTroopTile.
    }

    @Override
    public boolean canStepOn() {
        return false;
    }

    @Override
    public boolean hasTroop() {
        return troop != null;

    }
}
