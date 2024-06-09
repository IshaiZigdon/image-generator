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
        //Axis direction == axisDirection
        Vector axisDirection = axis.getDirection();
        //ray direction == rayDirection
        Vector rayDirection = ray.getDirection();


        if (isZero(axisDirection.dotProduct(rayDirection) - axisDirection.length() * rayDirection.length()))
            return null;

        //dv = axisDirection x rayDirection
        Vector dv = axisDirection.crossProduct(rayDirection);

        //a= |axisDirection x rayDirection|^2/|axisDirection|^2
        double a = alignZero((dv.lengthSquared()) / axisDirection.lengthSquared());

        if (ray.getHead().equals(axis.getHead())) {
            return List.of(ray.getPoint(alignZero(radius / sqrt(a))));
        }
        //but for now it will be null
        // w= distance between the point of ray and the point of axis
        Vector w = ray.getHead().subtract(axis.getHead());


        if (isZero(axisDirection.dotProduct(w) - axisDirection.length() * w.length())) {
//            double temp = rayDirection.dotProduct(axisDirection) / axisDirection.dotProduct(axisDirection);
//            if (temp != 0) {//im not sure if this always 0
//                Vector tempD = axisDirection.scale(-temp);
//                Vector v_90 = rayDirection.add(tempD);
//                return List.of(ray.getPoint(alignZero(radius / v_90.length())));
//            }
            return List.of(ray.getPoint(alignZero(radius / rayDirection.length())));
        }
        //dw = axisDirection x w
        Vector dw = axisDirection.crossProduct(w);
        //b = 2* (axisDirection x w) * (axisDirection x rayDirection)  / |axisDirection|^2
        double b = alignZero((2 * dv.dotProduct(dw)) / axisDirection.lengthSquared());
        //c = |axisDirection x w|^2 / |axisDirection|^2 - r^2
        double c = alignZero((dw.lengthSquared() / axisDirection.lengthSquared()) - radiusSquared);


        double discriminant = alignZero(b * b - 4 * a * c);
        if (discriminant >= 0) {
            double t1, t2;
            t1 = alignZero((-b - sqrt(discriminant)) / 2 * a);
            t2 = alignZero((-b + sqrt(discriminant)) / 2 * a);
            if (t2 <= 0)
                return null;
            if (t1 <= 0) {
                return List.of(ray.getPoint(t2));
            } else if (isZero(discriminant))//if the discriminant is zero then we "have" one point and else we
                //would return 2 points
                return null;
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }
        return null;
    }
}



