package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;


/**
 * Sphere class represent a 3D sphere and inherits from RadialGeometry
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Sphere extends RadialGeometry {
    /**
     * the center of the sphere
     */
    final private Point center;

    /**
     * constructor to initialize Sphere with a point and a radius
     *
     * @param p      the point that represent the center of the sphere
     * @param radius represent the radius of the sphere
     */
    public Sphere(Point p, double radius) {
        super(radius);
        center = p;
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector u;
        try {
            //try because if they are the same point we need to do a different calculation
            u = center.subtract(p0);
        } catch (IllegalArgumentException msg) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        //the length of the vector
        double tm = u.dotProduct(v);
        //the distance from the center to the vector
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = alignZero(radiusSquared - dSquared);
        if (thSquared <= 0)
            return null;

        double th = sqrt(thSquared);
        //calculates the distance in the point from the vector
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        //if t1 is bigger than maxDistance so do t2
        if (alignZero(t1 - maxDistance) >= 0) return null;

        if (alignZero(t2 - maxDistance) <= 0)
            return t1 <= 0
                    ? List.of(new GeoPoint(this, ray.getPoint(t2)))
                    : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        else
            return t1 <= 0 ? null
                    : List.of(new GeoPoint(this, ray.getPoint(t1)));
    }
}
