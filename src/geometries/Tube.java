package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
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
        Vector d =axis.getDirection();
        //ray direction ==v
        Vector v = ray.getDirection();
        // w= distance between the point of ray and the point of axis
        double a,b,c;
        //dv = d x v
        Vector dv;
        // w= distance between the point of ray and the point of axis
        Vector w;
        //dw = d x w
        Vector dw;
        try{
            dv = d.crossProduct(v);
            w =ray.getHead().subtract(axis.getHead());
            dw = d.crossProduct(w);
        }
        catch(IllegalArgumentException msg){
            return null;
        }
        //a= |d x v|^2/|d|^2
        a = alignZero( (dv.lengthSquared())/d.lengthSquared());
        //b = 2* (d x w) * (d x v)  / |d|^2
        b =alignZero( (2* dv.dotProduct(dw)) / d.lengthSquared());
        //c = |d x w|^2 / |d|^2 - r^2
        c =alignZero( (dw.lengthSquared()/ d.lengthSquared() )-radiusSquared);

        double discriminant = alignZero(b*b - 4*a*c);
        if (discriminant < 0) {
            return null;
        }
        double t1,t2;
        t1 = (-b-Math.sqrt(discriminant) ) / 2*a;
        t2 = (-b+Math.sqrt(discriminant) ) / 2*a;
        if (discriminant == 0||t2 ==0||t1==0) {
            //t1 is the point
            if(t1 ==0)
                return List.of(ray.getPoint(alignZero(t2)));
            return List.of(ray.getPoint(alignZero(t1)));
        }
        //t1 and t2 are the points
        //discriminant must be >0
        return List.of(ray.getPoint(alignZero(t1)), ray.getPoint(alignZero(t2)));


    }
}
