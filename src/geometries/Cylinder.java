package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class models a 3D shape with circular bases and a curved surface in
 * Cartesian coordinates inherits from Tube.
 *
 * @author Ishai
 * @author Zaki
 */
@SuppressWarnings("unused")
public class Cylinder extends Tube {
    /**
     * the height of the cylinder
     */
    final private double height;

    /**
     * constructor to initialize Cylinder based object with its ray, radius and height
     *
     * @param r      the ray that represent the axis
     * @param radius radius of the base of the cylinder
     * @param height height of the cylinder
     */
    @SuppressWarnings("unused")
    public Cylinder(Ray r, double radius, double height) {
        super(r, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        //if p is the same as p0, meaning p is in the middle of the base
        if (p.equals(axis.getHead()))
            return axis.getDirection();
        //the distance between p0 and o
        double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));
        if (isZero(t) || isZero(t - height))
            //meaning the distance between p0 and o is 0(p is on the base on which p0 is)
            //or height(p is on the other base), that also includes the points on the edges of the bases.
            //so the normal of all those points is the direction vector itself
            return axis.getDirection();
        //p is on the side surface
        return super.getNormal(p);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        var intersections = super.findGeoIntersectionsHelper(ray, maxDistance);
        List<GeoPoint> result = null;

        Vector aD = axis.getDirection();
        Point axisStart = axis.getHead();
        Point axisEnd = axisStart.add(aD.scale(height));
        Vector v = ray.getDirection();
        Point rayHead = ray.getHead();
        double d1 = axisStart.distance(rayHead);
        double d2 = axisEnd.distance(rayHead);

        //if ray is not parallel to axis
        if (intersections == null && !isZero(v.dotProduct(aD) - v.length() * aD.length()))
            return null;

        if (intersections != null) {
            //removing the points out of bounds
            for (GeoPoint gp : intersections) {
                //if the ray goes through one of the bases
                Vector v2 = axisStart.subtract(gp.point);
                Vector v3 = axisEnd.subtract(gp.point);
                if (isZero(v2.dotProduct(aD)) || isZero(v3.dotProduct(aD))) continue;

                Point p = gp.point;
                Vector vectorToAxisStart = p.subtract(axisStart);
                Vector vectorToAxisEnd = p.subtract(axisEnd);
                double dp = alignZero(vectorToAxisStart.dotProduct(vectorToAxisEnd));
                if (dp < 0) {
                    if (result == null)
                        result = new LinkedList<>();
                    result.add(gp);
                }
            }

            //no points were find
            if (result == null) return null;
            if (result.size() == 2) return result;
        }

        // Check for intersections with the cylinder caps
        Plane bottomCap = new Plane(axisStart, aD);
        Point p1 = basesIntersection(bottomCap, ray, maxDistance, axisStart);
        Plane topCap = new Plane(axisEnd, aD);
        Point p2 = basesIntersection(topCap, ray, maxDistance, axisEnd);

        if(p1 == null && p2 == null) return result;

        if (result == null)
            return List.of(new GeoPoint(this,p1),new GeoPoint(this,p2));
        GeoPoint gp = result.getFirst();
        if(gp.equals(intersections.getFirst()))
            return List.of(gp,new GeoPoint(this,p1 == null? p2:p1));
        return List.of(new GeoPoint(this,p1 == null? p2:p1),gp);
    }

    private Point basesIntersection(Plane base, Ray ray, double maxDistance,Point axisBase) {
        var baseIntersections = base.findGeoIntersections(ray, maxDistance);

        if (baseIntersections != null) {
            Point baseIntersection = baseIntersections.getFirst().point;
            if (baseIntersection.distance(axisBase) < radius) {
                return baseIntersection;
            }
        }
        return null;
    }
}
