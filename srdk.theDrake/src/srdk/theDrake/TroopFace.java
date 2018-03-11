package srdk.theDrake;

public enum TroopFace {
    AVERS, REVERS {
        @Override
        public TroopFace flipped(){
            return AVERS;
        }
    };

    public TroopFace flipped() {
        return REVERS;
    }
}
