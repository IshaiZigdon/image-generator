package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Triangle class, empty at this point
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Triangle extends Polygon {
    /**
     * Triangle constructor that receives the 3 points of the triangle
     * and call the father constructor
     *
     * @param a first point
     * @param b second point
     * @param c third point
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> lst = plane.findIntersections(ray);
        if (lst == null)
            return null;

        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double x1 = alignZero(v.dotProduct(n1));

        if (isZero(x1)) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double x2 = alignZero(v.dotProduct(n2));
        if (x1 * x2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double x3 = alignZero(v.dotProduct(n3));
        if (x1 * x3 <= 0) return null;

        return List.of(new GeoPoint(this, lst.getFirst()));
    }
}
