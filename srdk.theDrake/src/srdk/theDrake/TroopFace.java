package srdk.theDrake;

import javax.sound.sampled.ReverbType;

public enum TroopFace {
    AVERS(REVERS),
    REVERS(AVERS);

    private final TroopFace otherFace;

    private {
        AVERS.otherFace = REVERS;
        REVERS.otherFace = AVERS;
    }

    public TroopFace flipped() {
        return otherFace;
    }
}
