package geometries;
import primitives.*;

public class Tube extends RadialGeometry {
    final protected Ray axis;
    public Tube(Ray r,double radius) {
        super(radius);
        axis = r;
    }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
