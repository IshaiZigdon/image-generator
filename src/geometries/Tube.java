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
        double t1,t2,a,b,c;
        //a = Vx^2+ Vy^2
        Vector vec = ray.getDirection();
        Point p = ray.getPoint(0);
        a = vec.lengthSquared();//we need to somehow do it without the z
        //b = 2X0Vx+2Y0Vy
        b=
        //c = X0^2+Y0^2-R^2
        c= p.distanceSquared(Point.ZERO)-radiusSquared;
        //Δ = b^2-4ac
        //Δ<0: no points
        //Δ=0:one point (tangent so its zero too)
        //Δ>0: 2 points( could be one practicly because it can start in the middle
        return null;
    }
}
