package primitives;

public class Ray {
    private Point head;
    private Vector Direction;
    public Ray(Point head, Vector Direction) {
        this.head = head;
        this.Direction = Direction;
    }
    @Override
    public final boolean equals(Object obj){
        if(obj instanceof Ray){
            Ray r = (Ray)obj;
            return head.equals(r.head) && Direction.equals(r.Direction);
        }
        return false;
    }
    @Override
    public final String toString() {
        return "head=" + head + ", Direction=" + Direction;
    }

}
