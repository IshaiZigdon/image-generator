package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.List;

import static java.lang.Math.sqrt;

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
        //Axis direction ==d
        Vector d = axis.getDirection();
        //ray direction ==v
        Vector v = ray.getDirection();
        double a, b, c;

        //dv = d x v
        Vector dv;

        // w= distance between the point of ray and the point of axis
        Vector w;

        //dw = d x w
        Vector dw;

        if (d.dotProduct(v) == d.length() * v.length())
            return null;
        dv = d.crossProduct(v);

        //a= |d x v|^2/|d|^2
        a = alignZero((dv.lengthSquared()) / d.lengthSquared());

        if (ray.getHead().equals(axis.getHead())) {
            return List.of(ray.getPoint(alignZero(radius / sqrt(a))));
        }
        //but for now it will be null
        w = ray.getHead().subtract(axis.getHead());
        if (isZero(d.dotProduct(w) - d.length() * w.length())) {
            double temp = v.dotProduct(d) / d.dotProduct(d);
            if (temp != 0) {//im not sure if this always 0
                Vector tempD = d.scale(-temp);
                Vector v_90 = v.add(tempD);
                return List.of(ray.getPoint(alignZero(radius / v_90.length())));
            }
            return List.of(ray.getPoint(alignZero(radius / v.length())));
        }
        dw = d.crossProduct(w);
        //b = 2* (d x w) * (d x v)  / |d|^2
        b = alignZero((2 * dv.dotProduct(dw)) / d.lengthSquared());
        //c = |d x w|^2 / |d|^2 - r^2
        c = alignZero((dw.lengthSquared() / d.lengthSquared()) - radiusSquared);


        double discriminant = alignZero(b * b - 4 * a * c);
        if (discriminant >= 0) {
            double t1, t2;
            t1 = alignZero((-b - sqrt(discriminant)) / 2 * a);
            t2 = alignZero((-b + sqrt(discriminant)) / 2 * a);
            if (t2 <= 0)
                return null;
            if (t1 <= 0) {
                return List.of(ray.getPoint(alignZero(t2)));
            } else if (isZero(discriminant))//if the discriminant is zero then we "have" one point and else we
                //would return 2 points
                return null;
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }
        return null;
    }
}



