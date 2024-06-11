package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        // o = P0 +t*v
        // normal = (vector)p-o
        return p.subtract(axis.getPoint(t)).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        //Axis direction == d
        Vector d = axis.getDirection();
        //ray direction == v
        Vector v = ray.getDirection();

        if (isZero(d.dotProduct(v) - d.length() * v.length()))
            return null;

        //dv = d x v
        Vector dv = d.crossProduct(v);

        //a= |d x v|^2/|d|^2
        double a = (dv.lengthSquared()) / d.lengthSquared();

        if (ray.getHead().equals(axis.getHead())) {
            return List.of(ray.getPoint(alignZero(radius / sqrt(a))));
        }
        //but for now it will be null
        // w= distance between the point of ray and the point of axis
        Vector w = ray.getHead().subtract(axis.getHead());


        if (isZero(d.dotProduct(w) - d.length() * w.length())) {
            return List.of(ray.getPoint(alignZero(radius / v.length())));
        }

        //dw = d x w
        Vector dw = d.crossProduct(w);
        //b = 2* (d x w) * (d x v)  / |d|^2
        double b = (2 * dw.dotProduct(dv)) / d.lengthSquared();
        //c = |d x w|^2 / |d|^2 - r^2
        double c = (dw.lengthSquared() / d.lengthSquared()) - radiusSquared;


        double discriminant = b * b - 4 * a * c;
        if (discriminant >= 0) {
            double t1, t2;
            t1 = (-b - sqrt(discriminant)) / (2 * a);
            t2 = (-b + sqrt(discriminant)) / (2 * a);
            t1=alignZero(t1);
            t2=alignZero(t2);
            if (t2 <= 0)
                return null;
            if (t1 <= 0) {
                return List.of(ray.getPoint(t2));
            }
            if (isZero(discriminant))//if the discriminant is zero then we "have" one point and else we
                //would return 2 points
                return null;
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }
        return null;
    }
}



