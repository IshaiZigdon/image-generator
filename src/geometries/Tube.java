package geometries;
import primitives.*;

/**
 * Tube class represent a 3D tube, inherits from RadialGeometry
 */
public class Tube extends RadialGeometry {
    /** @todo idk what that is */
    final protected Ray axis;

    /**
     * constructor to initialize the Tube with a ray and a radius
     * @param r the axis ray @todo im not 100% sure
     * @param radius the radius of the tube
     */
    public Tube(Ray r,double radius) {
        super(radius);
        axis = r;
    }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
