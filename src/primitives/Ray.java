package primitives;

public class Ray {
    private final Point head;
    private final Vector direction;
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction;
    }
    @Override
    public final boolean equals(Object obj){
        if(obj instanceof Ray){
            Ray r = (Ray)obj;
            return head.equals(r.head) && direction.equals(r.direction);
        }
        return false;
    }
    @Override
    public final String toString() {
        return "head=" + head + ", direction=" + direction;
    }

}
