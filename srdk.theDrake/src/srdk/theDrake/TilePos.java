package srdk.theDrake;

public interface TilePos {
    public int i();
    public int j();
    public char column();
    public int row();

    public TilePos step(int columnStep, int rowStep);
    public TilePos step(Offset2D step);
    public boolean isNextTo(TilePos pos);
    public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side);

    public boolean equalsTo(int i, int j);
    public boolean equalsTo(char column, int row);

    public static final TilePos OFF_BOARD = new TilePos() {

        @Override
        public int i() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int j() {
            throw new UnsupportedOperationException();
        }

        @Override
        public char column() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int row() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TilePos step(int columnStep, int rowStep) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TilePos step(Offset2D step) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isNextTo(TilePos pos) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equalsTo(int i, int j) {
            return false;
        }

        @Override
        public boolean equalsTo(char column, int row) {
            return false;
        }
    };
}
