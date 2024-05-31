package geometries;
import java.util.*;
import primitives.*;

/**
 *
 */
public class Geometries implements Intersectable{
    private final List<Intersectable> intersectables = new LinkedList<>();
    private Geometries() {}

    /**
     *
     * @param geometries
     */
    public Geometries(Intersectable ... geometries) {

    }

    /**
     *
     * @param geometries
     */
    public void add(Intersectable ... geometries) {

    }
    /**
     * calculates where are the intersecting points of the
     * shape with the given ray
     *
     * @param ray the ray
     * @return list of intersecting points
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
