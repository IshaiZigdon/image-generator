package primitives;

import geometries.Intersectable.GeoPoint;

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
     * Delta value for accuracy
     */
    private static final double DELTA = 0.1;
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
     * ctor that moves the given point DELTA on the direction of given normal
     *
     * @param point     the given point
     * @param direction the given direction
     * @param normal    the given normal
     * @param dotP      to check if the normal and direction on the same direction
     */
    public Ray(Point point, Vector direction, Vector normal, double dotP) {
        Vector deltaVec = normal.scale(dotP < 0 ? DELTA : -DELTA);
        this.head = point.add(deltaVec);
        this.direction = direction;
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
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream()
                .map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * returns the closest GeoPoint in the list
     *
     * @param geoPoints list of the intersecting Geopoints of the geometries
     * @return the closest GeoPoint in the list if exist
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints == null)
            return null;

        GeoPoint closestGeoPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (GeoPoint geoPoint : geoPoints) {
            double distanceSquared = geoPoint.point.distanceSquared(head);
            if (distanceSquared < minDistance) {
                closestGeoPoint = geoPoint;
                minDistance = distanceSquared;
            }
        }
        return closestGeoPoint;
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
