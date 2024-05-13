package geometries;

import primitives.*;

/***
 * an abstract class that all the radial shapes are using
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public abstract class RadialGeometry implements Geometry {
    /**
     * the radius of the radial shape
     */
    final protected double radius;
    /**
     * the squared radius of the radial shape
     */
    final protected double radiusSquared;

    /***
     * constructor to initialize the radius of the radial shapes
     * @param radius the radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
        this.radiusSquared = radius * radius;
    }
}
