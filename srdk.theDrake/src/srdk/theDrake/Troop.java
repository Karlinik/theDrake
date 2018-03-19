package srdk.theDrake;

import java.util.ArrayList;
import java.util.List;

public class Troop {

    private final String name;
    private final Offset2D aversPivot;
    private final Offset2D reversPivot;
    private final List<TroopAction> aversActions;
    private final List<TroopAction> reversActions;

    public Troop(String name,
                 Offset2D aversPivot,
                 Offset2D reversPivot,
                 List<TroopAction> aversActions,
                 List<TroopAction> reversActions){
        this.name = name;
        this.aversPivot = aversPivot;
        this.reversPivot = reversPivot;
        this.aversActions = new ArrayList<>(aversActions);
        this.reversActions = new ArrayList<>(reversActions);
    }

    public Troop(String name,
                 Offset2D pivot,
                 List<TroopAction> aversActions,
                 List<TroopAction> reversActions){
        this(name, pivot, pivot, aversActions, reversActions);
    }

    public Troop(String name,
                 List<TroopAction> aversActions,
                 List<TroopAction> reversActions) {
        this(name,new Offset2D (1,1),new Offset2D (1,1), aversActions, reversActions);
    }

    public String name(){
        return name;
    }

    public Offset2D pivot(TroopFace face){
        if(face == TroopFace.AVERS) {
            return aversPivot;
        }
        else {
            return reversPivot;
        }
    }

    public List<TroopAction> actions(TroopFace face){
        if (face.equals(face.AVERS))
            return aversActions;
        return reversActions;
    }

}
