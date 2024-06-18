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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        //Axis direction == d   //ray direction == v
        Vector d = axis.getDirection(), v = ray.getDirection();

        if (isZero(d.dotProduct(v) - d.length() * v.length()))
            return null;
        //dv = d x v
        Vector dv = d.crossProduct(v);

        //a= |d x v|^2/|d|^2
        double a = (dv.lengthSquared()) / d.lengthSquared();

        if (ray.getHead().equals(axis.getHead()))
            return List.of(new GeoPoint(this, ray.getPoint(alignZero(radius / sqrt(a)))));

        // w= distance between the point of ray and the point of axis
        Vector w = ray.getHead().subtract(axis.getHead());

        if (isZero(d.dotProduct(w) - d.length() * w.length()))
            return List.of(new GeoPoint(this, ray.getPoint(alignZero(radius / v.length()))));

        //dw = d x w
        Vector dw = d.crossProduct(w);
        //b = 2* (d x w) * (d x v)  / |d|^2
        double b = (2 * dw.dotProduct(dv)) / d.lengthSquared();
        //c = |d x w|^2 / |d|^2 - r^2
        double c = (dw.lengthSquared() / d.lengthSquared()) - radiusSquared;
        double sqrtDiscriminant = sqrt(b * b - 4 * a * c);

        if (sqrtDiscriminant > 0) {
            double t1, t2;
            t1 = alignZero((-b - sqrtDiscriminant) / (2 * a));
            t2 = alignZero((-b + sqrtDiscriminant) / (2 * a));
            if (t2 <= 0)
                return null;
            return t1 <= 0 ?
                    List.of(new GeoPoint(this, ray.getPoint(t2)))
                    : List.of(new GeoPoint(this, ray.getPoint(t1)),
                    new GeoPoint(this, ray.getPoint(t2)));
        }
        return null;
    }
}



