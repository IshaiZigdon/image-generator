package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Geometries implements Intersectable {
    private final List<Intersectable> intersectables = new LinkedList<>();

    private Geometries() {
    }

    /**
     * @param geometries the shapes
     */
    public Geometries(Intersectable... geometries) {
        if (geometries != null)
            this.add(geometries);
    }

    /**
     * @param geometries the additional shapes to add
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
            List<Point> points = i.findIntersections(ray);
            if (points != null)
                result.addAll(points);
        }
        return result.isEmpty() ? null : result;
    }
}
