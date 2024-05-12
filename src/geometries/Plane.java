package geometries;

import primitives.*;

/***
 * this class represent an infinite plane field with a point and a vector
 */
public class Plane implements Geometry {
    /** a point on the plane field */
    private final Point q;
    /** a vector in the field  */
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
     * @return the normal vector @todo zaki go thtough it im not sure its good
     */
    public Vector getNormal() {
        return normal;
    }
}
