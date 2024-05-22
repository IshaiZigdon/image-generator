package geometries;

import primitives.*;

/**
 * Sphere class represent a 3D sphere and inherits from RadialGeometry
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Sphere extends RadialGeometry {
    final private Point center;

    /**
     *
     *
     * 
     * constructor to initialize Sphere with a point and a radius
     * @param p the point that represent the center of the sphere
     * @param radius represent the radius of the sphere
     */
    public Sphere(Point p, double radius) {
        super(radius);
        center = p;
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center);
    }
}
