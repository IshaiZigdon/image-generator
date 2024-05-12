package geometries;
import primitives.*;

public class Cylinder extends Tube{
    final private double height;
    public Cylinder(Ray r, double radius, double height) {
        super(r, radius);
        this.height = height;
    }
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
