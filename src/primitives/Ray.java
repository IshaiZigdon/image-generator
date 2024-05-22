package primitives;

/**
 * This class will represent a ray with point and vector
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * constructor to initialize ray with head and direction
     *
     * @param head      -> represent the head
     * @param direction -> represent the direction
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        ///if its already normalized, it will not change
        this.direction = direction.normalize();
    }

    /**
     * get function for head
     * @return head
     */
    public Point getHead() {
        return head;
    }

    /**
     * get function for direction
     * @return direction
     */
    public Vector getDirection() {
        return direction;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Ray r && head.equals(r.head) && direction.equals(r.direction);
    }

    @Override
    public final String toString() {
        return "Ray:" + head.xyz + "->" + direction.xyz;
    }
}
