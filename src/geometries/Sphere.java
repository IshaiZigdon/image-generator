package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    final private Point center;
    public Sphere(Point p,double radius) {
        super(radius);
        center = p;
    }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
