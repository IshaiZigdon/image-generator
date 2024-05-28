package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Cylinder class models a 3D shape with circular bases and a curved surface in
 * Cartesian coordinates inherits from Tube.
 *
 * @author Ishai
 * @author Zaki
 */
@SuppressWarnings("unused")
public class Cylinder extends Tube {
    /**
     * the height of the cylinder
     */
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
        //if p is the same as p0, meaning p is in the middle of the base
        if (p.equals(axis.getHead()))
            return axis.getDirection();
        //the distance between p0 and o
        double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));
        if (isZero(t) || isZero(t - height))
            //meaning the distance between p0 and o is 0(p is on the base on which p0 is)
            //or height(p is on the other base), that also includes the points on the edges of the bases.
            //so the normal of all those points is the direction vector itself
            return axis.getDirection();
        //p is on the side surface
        return super.getNormal(p);
    }
}
