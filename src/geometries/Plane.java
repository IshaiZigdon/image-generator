package geometries;

import primitives.*;

public class Plane implements Geometry {
    private final Point q;
    private final Vector normal;
    public Plane(Point a, Point b, Point c) {
        q = a;
        normal = null;
    }
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }
    @Override
    public Vector getNormal(Point p) {
        return normal;
    }
    public Vector getNormal() {
        return normal;
    }
}
