package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.*;

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
        double t1, t2, a, b, c;
        //a = Vx^2+ Vy^2
        Vector vec = ray.getDirection();
        Point p ;
        a = vec.lengthSquared();//we need to somehow do it without the z
        //b = 2X0Vx+2Y0Vy
        p=ray.getPoint(2);
        b = 0;
        //c = X0^2+Y0^2-R^2
        c = ray.getHead().distanceSquared(Point.ZERO) - radiusSquared;
        //Δ = b^2-4ac
        double delta = b * b - 4 * a * c;
        //Δ<0: no points
        //Δ=0:one point (tangent so its zero too)
        if (delta <= 0)
            return null;
        //Δ>0: 2 points( could be one practically because it can start in the middle
        //*2 points
        t1 = (-b - sqrt(delta)) / 2 * a;
        t2 = (b - sqrt(delta)) / 2 * a;
        List<Point> result = new LinkedList<>();
        result.add(ray.getPoint(t1));
        result.add(ray.getPoint(t2));
        //* one point
        return null;
    }
}
