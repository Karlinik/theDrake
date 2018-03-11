package srdk.theDrake;

public class Offset2D{

    public final int x;
    public final int y;

    public Offset2D ( int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equalsTo (int x , int y){
        /*if(new Offset2D(x,y) == new Offset2D(x,y))
            return true;
        else
            return false;*/
        return x == this.x && y == this.y;
    }

    public Offset2D yFlipped () {
        //new Offset2D(x, y);
        return new Offset2D(x, -y);

    }

}
