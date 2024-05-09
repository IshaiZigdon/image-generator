package primitives;
/**
 * This class will represent a ray with point and vector
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Ray {
    /** coordinates of where the ray begins*/
    private final Point head;
    /**direction of where the ray is moving towards */
    private final Vector direction;
    /**
     * constructor to initialize ray with head and direction
     * @param head -> represent the head
     * @param direction -> represent the direction
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        if(direction.length() != 1)
            this.direction= direction.normalize();
        else
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
