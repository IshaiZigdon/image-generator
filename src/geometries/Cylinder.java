package geometries;

import primitives.*;

/**
 * Cylinder class models a 3D shape with circular bases and a curved surface in
 * Cartesian coordinates inherits from Tube.
 *
 * @author Ishai
 * @author Zaki
 */
@SuppressWarnings("unused")
public class Cylinder extends Tube {
    final private double height;

    /**
     * constructor to initialize Cylinder based object with its ray, radius and height
     *
     * @param r      the ray that represent the axis
     * @param radius radius of the base of the cylinder
     * @param height height of the cylinder
     */
    @SuppressWarnings("unused")
    public Cylinder(Ray r, double radius, double height) {
        super(r, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        /*
         * //t = v * (p-p0)
         *         double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));
         *         //o = P0 +t*v
         *         if (t==0)//normal =p-p0 (vector)
         *            return p.subtract(axis.getHead()).normalize();
         *         else// normal = p-o (vector)
         *             return p.subtract(axis.getHead().add(axis.getDirection().scale(t))).normalize();
         */

        //if p is the same as p0
        if(p.equals(axis.getHead()))
            return axis.getDirection();
        //if p on base 1 or base 2 or in the mid of base 2
        double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));
        if (t==0 || t==height)//normal =p-p0 (vector)
            return axis.getDirection() ;
        //if is on the side surface
        return super.getNormal(p);
    }
}
