package geometries;
import primitives.*;
public class Tube extends RadialGeometry {
    /** the axis of the tube*/
    final protected Ray axis;
    /**
     * constructor to initialize the Tube with a ray and a radius
     * @param r the axis ray
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
