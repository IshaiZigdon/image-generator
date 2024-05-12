package geometries;
import primitives.*;
/**
 * Cylinder class models a 3D shape with circular bases and a curved surface in
 * Cartesian coordinates inherits from Tube.
 * @author Ishai
 * @author Zaki
 */
public class Cylinder extends Tube{
    /**height of the cylinder*/
    final private double height;

    /**
     * constructor to initialize Cylinder based object with its ray, radius and height
     * @param r the ray that @todo zaki idk how to say what it does
     * @param radius radius of the base of the cylinder
     * @param height height of the cylinder
     */
    public Cylinder(Ray r, double radius, double height) {
        super(r, radius);
        this.height = height;
    }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
