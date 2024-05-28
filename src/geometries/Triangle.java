package geometries;

import primitives.Point;
import primitives.Ray;

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
        return null;
    }
}
