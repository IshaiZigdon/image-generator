package primitives;

import static primitives.Util.isZero;

/**
 * This class will represent a ray with point and vector
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Ray {
    /**
     * head point of the ray
     */
    private final Point head;
    /**
     * the direction of the ray
     */
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
     *
     * @return head
     */
    public Point getHead() {
        return head;
    }

    /**
     * get function for direction
     *
     * @return direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * im sexy and i know it
     * @param t
     * @return
     */
    public Point getPoint(double t)
    {
        return isZero(t)? head : head.add(direction.scale(t));
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
