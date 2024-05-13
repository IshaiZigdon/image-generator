package geometries;

import primitives.*;

/***
 * this class represent an infinite plane field with a point and a vector
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Plane implements Geometry {
    private final Point q;
    private final Vector normal;

    /***
     * a constructor to calculate the plane with 3 points, calculates the normal
     * based on the calculations of normal to a triangle
     * @param a first point
     * @param b second point
     * @param c third point
     */
    public Plane(Point a, Point b, Point c) {
        q = a;
        normal = null;
    }

    /***
     * constructor to initialize Plane with a point and a vector
     * @param q the point
     * @param normal the vector
     */
    @SuppressWarnings("unused")
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /***
     * a function to return the normal of the plane
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }
}
