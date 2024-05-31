package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public List<Point> findIntersections(Ray ray) {
        List<Point> lst = plane.findIntersections(ray);
        if (lst == null)
            return null;
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector v1;
        Vector v2;
        Vector v3;
        Vector n1;
        Vector n2;
        Vector n3;
        try {
            v1 = vertices.get(0).subtract(p0);
            v2 = vertices.get(1).subtract(p0);
            v3 = vertices.get(2).subtract(p0);
            n1 = v1.crossProduct(v2).normalize();
            n2 = v2.crossProduct(v3).normalize();
            n3 = v3.crossProduct(v1).normalize();
        } catch (IllegalArgumentException msg) {
            return null;
        }
        double x1 = v.dotProduct(n1);
        double x2 = v.dotProduct(n2);
        double x3 = v.dotProduct(n3);
        if ((x1 > 0 && x2 > 0 && x3 > 0) || (x1 < 0 && x2 < 0 && x3 < 0)) {
            return lst;
        }
        return null;
    }
}
