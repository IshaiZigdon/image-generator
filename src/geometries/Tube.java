package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * this class represents a 3D tube inherits from RadialGeometry
 *
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
     *
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
        //normal = (vector)p-p0
        // o = P0 +t*v
        // normal = (vector)p-o
        return p.subtract(axis.getPoint(t)).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
