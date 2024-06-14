package primitives;

import java.util.List;

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
     * return a point on the ray, in distance t from head
     *
     * @param t the length
     * @return the point
     */
    public Point getPoint(double t) {
        return isZero(t) ? head : head.add(direction.scale(t));
    }

    /**
     * returns the closest point in the list
     *
     * @param points list of the intersect points of the current shape
     * @return the closest point in the list if exist
     */
    public Point findClosestPoint(List<Point> points) {
        if (isZero(points.size()))
            return null;
        Point closestPoint = points.get(0);
        for (Point point : points) {
            if (head.distance(point) < head.distance(closestPoint)) {
                closestPoint = point;
            }
        }
        return closestPoint;
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
