package geometries;

import primitives.*;

/***
 * Sphere class represent a 3D sphere and inherits from RadialGeometry
 * @todo we sure that its extends from RadialGeometry?
 */
public class Sphere extends RadialGeometry{
    /** a point that represent the center of the sphere */
    final private Point center;

    /***
     * constructor to initialize Sphere with a point and a radius
     * @param p the point that represent the center of the sphere
     * @param radius represent the radius of the sphere
     */
    public Sphere(Point p,double radius) {
        super(radius);
        center = p;
    }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
