package geometries;

import java.util.*;

import primitives.*;

/**
 *
 */
public class Geometries implements Intersectable {
    private final List<Intersectable> intersectables = new LinkedList<>();

    private Geometries() {
    }

    /**
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        if (geometries == null)
            return;
        intersectables.addAll(Arrays.asList(geometries));
    }

    /**
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        intersectables.addAll(Arrays.asList(geometries));
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
        if (intersectables.isEmpty())
            return null;
        List<Point> result = new LinkedList<>();

        for (Intersectable i : intersectables) {
            List<Point> d = i.findIntersections(ray);
            if (d != null)
                result.addAll(d);
        }
        return result.isEmpty() ? null : result;
    }
}
