package geometries;

import primitives.*;

/***
 * an abstract class that all the radial shapes are using
 */
public abstract class RadialGeometry implements Geometry {
    /** the radius of the radial shape */
    final double radius;

    /***
     * constructor to initialize the radius with @todo look at double3 idk if i should write based object
     * @param radius the radius
     */
    public RadialGeometry(double radius){
        this.radius = radius;
    }
}
