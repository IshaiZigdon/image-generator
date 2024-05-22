package geometries;

import primitives.*;

/**
 * this class represents a 3D tube inherits from RadialGeometry
 * @author Ishai
 * @author Zaki
 */
public class Tube extends RadialGeometry {
    /**
     * the axis of the tube
     */
    final protected Ray axis;

    /**
     * constructor to initialize the Tube with a ray and a radius
     * @param r      the axis ray
     * @param radius the radius of the tube
     */
    public Tube(Ray r, double radius) {
        super(radius);
        axis = r;
    }

    @Override
    public Vector getNormal(Point p) {
        //t = v * (p-p0)
        double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));
        if (t==0)//normal = (vector)p-p0
           return p.subtract(axis.getHead()).normalize();
        //o = P0 +t*v
        else// normal = (vector)p-o
            return p.subtract(axis.getHead().add(axis.getDirection().scale(t))).normalize();
    }
}
