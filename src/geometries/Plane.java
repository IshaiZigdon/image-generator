package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * this class represent an infinite plane field with a point and a vector
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Plane extends Geometry {
    /**
     * point on the plane
     */
    private final Point q;
    /**
     * normal of the plane
     */
    private final Vector normal;

    /**
     * a constructor to calculate the plane with 3 points, calculates the normal
     * based on the calculations of normal to a triangle
     *
     * @param a first point
     * @param b second point
     * @param c third point
     */
    public Plane(Point a, Point b, Point c) {
        q = a;
        Vector v1 = b.subtract(a);
        Vector v2 = c.subtract(a);
        normal = v1.crossProduct(v2).normalize();
    }

    /**
     * constructor to initialize Plane with a point and a vector
     *
     * @param q      the point
     * @param normal the vector
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    @Override
    public void setMinMax(){
        min = null;
        max = null;
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * a function to return the normal of the plane
     *
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        double nv = normal.dotProduct(v);
        if (isZero(nv))
            return null;

        double nQMinusP0;
        try {
            nQMinusP0 = normal.dotProduct(q.subtract(p0));
        } catch (IllegalArgumentException msg) {
            return null;
        }

        double t = alignZero(nQMinusP0 / nv);
        return t <= 0 || alignZero(t - maxDistance) >= 0 ?
                null :
                List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}
