package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.*;

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
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector u;
        Point p1;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException msg) {
            p1 = center.add(v.scale(radius));
            return List.of(p1);
        }
        double tm = u.dotProduct(v);
        double dSquared = u.lengthSquared() - tm * tm;
        if (isZero(dSquared - radiusSquared))
            return null;
        double thSquared = radiusSquared - dSquared;
        if (thSquared < 0)
            return null;
        double th = sqrt(thSquared);
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0)
            return null;
        List<Point> lst = new ArrayList<>();
        if (t1 > 0)
            lst.add(p0.add(v.scale(t1)));
        if (t2 > 0)
            lst.add(p0.add(v.scale(t2)));
        return lst;
    }
}
