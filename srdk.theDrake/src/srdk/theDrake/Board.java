package srdk.theDrake;

public class Board {

    private final int dimension;
    private final BoardTile[][] board;

    public Board(int dimension) {
        this.dimension = dimension;
        board = new BoardTile[dimension][dimension];
        for(int i=0; i<dimension;i++)
            for (int j=0;j<dimension;j++){
                board[i][j]=board[i][j].EMPTY;
            }
    }

    private Board(BoardTile[][] board){
        this.dimension = board.length;
        this.board = board;
    }

    private BoardTile[][] cloneBoard(){
        BoardTile[][] tmp = new BoardTile[dimension][];
        for(int i=0;i<dimension;i++){
            tmp[i]=board[i].clone();
        }
        return tmp;
    }

    private BoardTile[][] fillBoard(BoardTile[][] tmp, TileAt... ats) {

        for(TileAt tile : ats) {
            tmp[tile.pos.i][tile.pos.j]=tile.tile;
        }
        return tmp;
    }

    public int dimension() {
        return dimension;
    }

    public BoardTile at(Board.Pos pos) {
        return board[pos.i][pos.j];
    }

    public Board withTiles(TileAt ...ats) {

        return new Board(fillBoard(cloneBoard(), ats));
    }

    public Pos pos(int i, int j) {
        return new Pos(i, j);
    }

    public Pos pos(char column, int row) {
        return new Pos(column, row);
    }

    public Pos pos(String pos) {
        return new Pos(pos);
    }

    public static class TileAt {
        public final Board.Pos pos;
        public final BoardTile tile;

        public TileAt(Board.Pos pos, BoardTile tile) {
            this.pos = pos;
            this.tile = tile;
        }
    }

    public class Pos implements TilePos {
        private final int i;
        private final int j;

        private Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        private Pos(char column, int row) {
            this.i = iFromColumn(column);
            this.j = jFromRow(row);
        }

        private Pos(String pos) {
            this(pos.charAt(0), Integer.parseInt(pos.substring(1)));
        }

        @Override
        public int i() {
            return i;
        }

        @Override
        public int j() {
            return j;
        }

        @Override
        public char column() {
            return (char) ('a' + i);
        }

        @Override
        public int row() {
            return j + 1;
        }

        public TilePos step(int columnStep, int rowStep) {
            int newi = i + columnStep;
            int newj = j + rowStep;

            if((newi >= 0 && newi < dimension) &&
                    (newj >= 0 && newj < dimension)) {
                return new Pos(newi, newj);
            }

            return TilePos.OFF_BOARD;
        }

        @Override
        public TilePos step(Offset2D step) {
            return step(step.x, step.y);
        }

        @Override
        public boolean isNextTo(TilePos pos) {
            if(pos == TilePos.OFF_BOARD)
                return false;

            if(this.i == pos.i() && Math.abs(this.j - pos.j()) == 1)
                return true;

            if(this.j == pos.j() && Math.abs(this.i - pos.i()) == 1)
                return true;

            return false;
        }

        @Override
        public TilePos stepByPlayingSide(Offset2D dir, PlayingSide side) {
            return side == PlayingSide.BLUE ?
                    step(dir) :
                    step(dir.yFlipped());
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + i;
            result = prime * result + j;
            return result;
        }

        @Override
        public boolean equalsTo(int i, int j) {
            return this.i == i && this.j == j;
        }

        @Override
        public boolean equalsTo(char column, int row) {
            return this.i == iFromColumn(column) && this.j == jFromRow(row);
        }

        private int iFromColumn(char column) {
            return column - 'a';
        }

        private int jFromRow(int row) {
            return row - 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Board.Pos other = (Board.Pos) obj;
            return this.equalsTo(other.i, other.j);
        }

        @Override
        public String toString() {
            return String.format("%c%d", column(), row());
        }
    }
}


